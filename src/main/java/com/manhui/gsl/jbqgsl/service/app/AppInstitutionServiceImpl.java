package com.manhui.gsl.jbqgsl.service.app;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manhui.gsl.jbqgsl.common.util.RemoveUeditorStyleUtil;
import com.manhui.gsl.jbqgsl.dao.app.AppInstitutionMapper;
import com.manhui.gsl.jbqgsl.dao.app.AppUserMapper;
import com.manhui.gsl.jbqgsl.model.AppUser;
import com.manhui.gsl.jbqgsl.model.ForthwithEvaluate;
import com.manhui.gsl.jbqgsl.model.Institution;
import com.manhui.gsl.jbqgsl.model.PaginationVO;
import com.manhui.gsl.jbqgsl.model.User;

@Service
public class AppInstitutionServiceImpl implements IAppInstitutionService {
	@Autowired
	private AppInstitutionMapper appInstitutionMapper;
	@Autowired
	private AppUserMapper appUserMapper;

	@Override
	public PaginationVO<Institution> queryInstitutionsByIds(List<String> passiveIdsList, long pageNo, long pageSize) {
		PaginationVO<Institution> pg = new PaginationVO<>();
		long total = appInstitutionMapper.getTotal(passiveIdsList);
		pg.setTotal(total);
		pageNo = (pageNo - 1) * pageSize;
		List<AppUser> headImgList = appUserMapper.queryInstitutionHeadPhoto(passiveIdsList);
		List<Institution> queryInstitutionsByIds = appInstitutionMapper.queryInstitutionsByIds(passiveIdsList, pageSize,
				pageNo);
		// 对比机构id给被评价机构赋值 图像
		for (AppUser user : headImgList) {
			for (Institution institution : queryInstitutionsByIds) {
				if (institution.getInstitution_id().equals(user.getInstitution_id())) {
					institution.setHeadImg(user.getHead_img());
				}
			}
		}
		pg.setDataList(queryInstitutionsByIds);
		return pg;
	}

	@Override
	public PaginationVO<Institution> queryInstitutions(List<String> passiveIdsList, long pageNo, long pageSize) {
		PaginationVO<Institution> pg = new PaginationVO<>();
		long total = appInstitutionMapper.countTotal(passiveIdsList);
		pg.setTotal(total);
		pageNo = (pageNo - 1) * pageSize;
		List<Institution> institutionList = appInstitutionMapper.queryInstitutions2(passiveIdsList, pageNo, pageSize);
		pg.setDataList(institutionList);
		return pg;
	}

	@Override
	public String queryInstitutionType(String institution_id) {
		return appInstitutionMapper.queryInstitutionType(institution_id);
	}

	@Override
	public PaginationVO<Institution> queryInstitutions(long pageNo, long pageSize) {
		PaginationVO<Institution> pg = new PaginationVO<>();
		long total = appInstitutionMapper.countTotal2();
		pg.setTotal(total);
		pageNo = (pageNo - 1) * pageSize;
		List<Institution> institutionList = appInstitutionMapper.queryInstitutions(pageNo, pageSize);
		pg.setDataList(institutionList);
		return pg;
	}

	/**
	 * 即时评价 --查询未被评价的所有政府机构
	 */
	@Override
	public PaginationVO<Institution> queryInstitutions2(Long pageNo, Long pageSize, String institution_type) {
		PaginationVO<Institution> pg = new PaginationVO<>();
		Long total = appInstitutionMapper.getTotal2(institution_type);
		pg.setTotal(total);
		if (pageNo != null) {
			pageNo = (pageNo - 1) * pageSize;

		}
		List<Institution> queryInstitutionsByIds = appInstitutionMapper.queryInstitutionsByIds2(pageSize, pageNo,
				institution_type);
		if(queryInstitutionsByIds !=null && queryInstitutionsByIds.size()>0) {
			for (Institution institution : queryInstitutionsByIds) {
				institution.setHtml_describe(institution.getInstitution_describe());
				if(institution.getInstitution_describe() !=null && !"".equals(institution.getInstitution_describe())) {
					String text = RemoveUeditorStyleUtil.getTextFromHtml(institution.getInstitution_describe());
					institution.setInstitution_describe(text);
				}
			}
		}
		// 对比机构id给被评价机构赋值 图像
		pg.setDataList(queryInstitutionsByIds);
		return pg;
	}

	@Override
	public Institution queryInstitutionInfo(String passive_id) {
		Institution institution = appInstitutionMapper.queryInstitutionInfo(passive_id);
		if(institution !=null &&institution.getInstitution_describe() !=null &&!"".equals(institution.getInstitution_describe() )) {
			institution.setHtml_describe(institution.getInstitution_describe());
			String textFromHtml = RemoveUeditorStyleUtil.getTextFromHtml(institution.getInstitution_describe());
			institution.setInstitution_describe(textFromHtml);
		}
		return institution;
	}

	/**
	 * 企业评价政府 按照政府的类型进行分类查找[1:区级 2:街道]
	 */
	@Override
	public PaginationVO<Institution> queryInstitutionsTypeGroupByIds(List<String> passiveIdsList,
			String institution_type, Long pageNo, Long pageSize) {
		PaginationVO<Institution> pg = new PaginationVO<>();
		long total = appInstitutionMapper.getTotal3(passiveIdsList, institution_type);
		pg.setTotal(total);
		if (pageNo != null) {
			pageNo = (pageNo - 1) * pageSize;
		}
		List<AppUser> headImgList = appUserMapper.queryInstitutionHeadPhoto(passiveIdsList);
		List<Institution> queryInstitutionsByIds = appInstitutionMapper.queryInstitutionsTypeGroupByIds(passiveIdsList,
				institution_type, pageNo, pageSize);
		// 对比机构id给被评价机构赋值 图像
		for (Institution institution : queryInstitutionsByIds) {
			if (institution.getInstitution_describe() != null && !"".equals(institution.getInstitution_describe())) {
				institution.setHtml_describe(institution.getInstitution_describe());
				String text = RemoveUeditorStyleUtil.getTextFromHtml(institution.getInstitution_describe());//去掉富文本标签
				institution.setInstitution_describe(text);
				institution.getInstitution_describe().trim();
			}
			for (AppUser user : headImgList) {
				if (institution.getInstitution_id().equals(user.getInstitution_id())) {
					institution.setHeadImg(user.getHead_img());
				}
			}
		}
		pg.setDataList(queryInstitutionsByIds);
		return pg;
	}

	/**
	 * 根据被评价机构id查询被评价机构主管企业 其他企业
	 */
	@Override
	public PaginationVO<Institution> queryInstitutionsCompanyTypeByIds(List<String> passiveIdsList,
			String institution_id, Long pageNo, Long pageSize) {
		PaginationVO<Institution> pg = new PaginationVO<Institution>();
		// 查询企业 总数
		long total = appInstitutionMapper.getTotal4(passiveIdsList);
		pg.setTotal(total);
		if (pageNo != null) {
			pageNo = (pageNo - 1) * pageSize;
		}
		// 查询企业详细信息
		List<AppUser> headImgList = appUserMapper.queryInstitutionHeadPhoto(passiveIdsList);
		List<Institution> queryInstitutionsByIds = appInstitutionMapper
				.queryInstitutionsCompanyTypeByIds(passiveIdsList, pageNo, pageSize);
		// 对比机构id给被评价机构赋值 图像
		for (Institution institution : queryInstitutionsByIds) {
			if (institution.getInstitution_describe() != null && !"".equals(institution.getInstitution_describe())) {
				institution.setHtml_describe(institution.getInstitution_describe());
				String text = RemoveUeditorStyleUtil.getTextFromHtml(institution.getInstitution_describe());//去掉富文本标签
				institution.setInstitution_describe(text);
				institution.getInstitution_describe().trim();
			}
			for (AppUser user : headImgList) {
				if (institution.getInstitution_id().equals(user.getInstitution_id())) {
					institution.setHeadImg(user.getHead_img());
				}
			}
		}
		pg.setDataList(queryInstitutionsByIds);
		return pg;
	}

	@Override
	public PaginationVO<Institution> queryInstitutionsCompanyTypeByIds2(List<String> passiveIdsList,
			String institution_type, String institution_id, String industry_id, Long pageNo, Long pageSize,
			String flag) {
		PaginationVO<Institution> pg = new PaginationVO<Institution>();
		long total = appInstitutionMapper.getTotal5(passiveIdsList, institution_type, industry_id, flag,
				institution_id);
		pg.setTotal(total);
		if (pageNo != null) {
			pageNo = (pageNo - 1) * pageSize;
		}
		// 查询企业详细信息
		List<AppUser> headImgList = appUserMapper.queryInstitutionHeadPhoto(passiveIdsList);
		List<Institution> queryInstitutionsByIds = appInstitutionMapper
				.queryInstitutionsCompanyTypeByIds2(passiveIdsList, institution_type, industry_id, pageNo, pageSize);
		// 对比机构id给被评价机构赋值 图像
		for (Institution institution : queryInstitutionsByIds) {
			if (institution.getInstitution_describe() != null && !"".equals(institution.getInstitution_describe())) {
				institution.setHtml_describe(institution.getInstitution_describe());
				String text = RemoveUeditorStyleUtil.getTextFromHtml(institution.getInstitution_describe());//去掉富文本标签
				institution.setInstitution_describe(text);
				institution.getInstitution_describe().trim();
			}
			for (AppUser user : headImgList) {
				if (institution.getInstitution_id().equals(user.getInstitution_id())) {
					institution.setHeadImg(user.getHead_img());
				}
			}
		}
		pg.setDataList(queryInstitutionsByIds);
		return pg;
	}

	@Override
	public Institution getInstitutionIntroduce(String institution_id) {
		
		return appInstitutionMapper.getInstitution(institution_id);
	}

}
