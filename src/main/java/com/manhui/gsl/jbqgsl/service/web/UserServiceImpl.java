package com.manhui.gsl.jbqgsl.service.web;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.manhui.gsl.jbqgsl.dao.UserMapper;
import com.manhui.gsl.jbqgsl.model.Dept;
import com.manhui.gsl.jbqgsl.model.Func;
import com.manhui.gsl.jbqgsl.model.FuncUrl;
import com.manhui.gsl.jbqgsl.model.Icon;
import com.manhui.gsl.jbqgsl.model.Menu;
import com.manhui.gsl.jbqgsl.model.Position;
import com.manhui.gsl.jbqgsl.model.PositionFunc;
import com.manhui.gsl.jbqgsl.model.User;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper organizationalMapper;

    @Override
    public List<Dept> queryDeptTree( Dept dept ) {
        return organizationalMapper.queryDeptTree( dept );
    }

    @Override
    public List<Dept> querySysDept( Dept dept ) {
        return organizationalMapper.querySysDept( dept );
    }

    @Override
    public List<Dept> queryDeptWhere( Dept dept ) {
        return organizationalMapper.queryDeptWhere( dept );
    }

    @Override
    public Integer querySysDeptSize( Dept dept ) {
        return organizationalMapper.querySysDeptSize( dept );
    }

    @Override
    public List<Position> queryPosition( Position position ) {
        return organizationalMapper.queryPosition( position );
    }

    @Override
    public Integer queryPositionCount( Position position ) {
        return organizationalMapper.queryPositionCount( position );
    }

    //
    @Override
    public List<User> queryPositionUser( User user ) {
        return organizationalMapper.queryPositionUser( user );
    }

    //
    @Override
    public Integer queryPositionUserCount( User user ) {
        return organizationalMapper.queryPositionUserCount( user );
    }
//
    @Override
    public List<User> queryUserList( User user ) {
        return organizationalMapper.queryUserList( user );
    }
//
    @Override
    public Integer queryUserListCount( User user ) {
        return organizationalMapper.queryUserListCount( user );
    }

    //kevin
    @Override
    public User queryUserWhere( User user ) {
        return organizationalMapper.queryUserWhere( user );
    }

    @Override
    public Integer insertDept( Dept dept ) {
        return organizationalMapper.insertDept( dept );
    }

    @Override
    public Integer deleteDept( String dept_id ) {
        Dept dept = new Dept();
        dept.setDept_id( dept_id );
        Dept dept1 = organizationalMapper.queryDeptWhere( dept ).get( 0 );
        Dept dept2 = new Dept();
        dept2.setP_dept_id( dept_id );
        List<Dept> depts = organizationalMapper.queryDeptWhere( dept2 );
        Integer bools = 0;
        for ( Dept dept3 : depts ) {
            dept3.setP_dept_id( dept1.getP_dept_id() );
            organizationalMapper.updateDept( dept3 );
        }
        Position position = new Position();
        position.setDept_id( dept.getDept_id() );
        List<Position> positions = organizationalMapper.queryPosition( position );
        for ( Position position1 : positions ) {
            organizationalMapper.deletePosition( position1 );
        }
        bools = organizationalMapper.deleteDept( dept );
        return bools;
    }

    @Override
    public Integer insertPosition( Position position ) {
        return organizationalMapper.insertPosition( position );
    }

    @Override
    public Integer updatePosition( Position position ) {
        return organizationalMapper.updatePosition( position );
    }

    @Override
    public Integer deletePosition( Position position ) {
        Integer bools = organizationalMapper.deletePosition( position );
        if ( bools > 0 ) {
            organizationalMapper.deletePositionToUser( position );
        }
        return bools;
    }

    @Override
    public Integer insertPositionToUser( Position position ) {
        return organizationalMapper.insertPositionToUser( position );
    }

    @Override
    public Integer deletePositionToUser( Position position ) {
        return organizationalMapper.deletePositionToUser( position );
    }

    @Override
    public Integer updateDept( Dept dept ) {
        return organizationalMapper.updateDept( dept );
    }

    @Override
    public List<Menu> queryMenuList( Menu menu ) {
        return organizationalMapper.queryMenuList( menu );
    }

    @Override
    public List<Menu> queryMenuWhere( Menu menu ) {
        return organizationalMapper.queryMenuWhere( menu );
    }

    @Override
    public Integer insertMenu( Menu menu ) {
        return organizationalMapper.insertMenu( menu );
    }

    @Override
    public Integer updateMenu( Menu menu ) {
        return organizationalMapper.updateMenu( menu );
    }

    @Override
    public Integer deleteMenu( Menu menu ) {
        List<Menu> menuList = organizationalMapper.queryMenuTree( menu );
        for ( Menu menu1 : menuList ) {
            Func func = new Func();
            func.setMenu_id( menu1.getMenu_id() );
            List<Func> funcs = organizationalMapper.queryFuncList( func );
            for ( Func func1 : funcs ) {
                FuncUrl funcUrl = new FuncUrl();
                funcUrl.setFunc_id( func.getFunc_id() );
                List<FuncUrl> funcUrls = organizationalMapper.queryFuncUrlList( funcUrl );
                for ( FuncUrl funcUrl1 : funcUrls ) {
                    organizationalMapper.deleteFuncUrl( funcUrl1 );
                }
                organizationalMapper.deleteFunc( func1 );
            }
            organizationalMapper.deleteMenu( menu1 );
        }
        return 1;
    }

    @Override
    public List<Func> queryFuncList( Func func ) {
        return organizationalMapper.queryFuncList( func );
    }

    @Override
    public Integer queryFuncListCount( Func func ) {
        return organizationalMapper.queryFuncListCount( func );
    }

    @Override
    public Func queryFuncWhere( Func func ) {
        return organizationalMapper.queryFuncWhere( func );
    }

    @Override
    public Integer insertFunction( Func func ) {
        return organizationalMapper.insertFunction( func );
    }

    @Override
    public Integer updateFunc( Func func ) {
        return organizationalMapper.updateFunc( func );
    }

    @Override
    public Integer deleteFunc( Func func ) {
        FuncUrl funcUrl = new FuncUrl();
        funcUrl.setFunc_id( func.getFunc_id() );
        List<FuncUrl> funcUrls = organizationalMapper.queryFuncUrlList( funcUrl );
        for ( FuncUrl funcUrl1 : funcUrls ) {
            organizationalMapper.deleteFuncUrl( funcUrl1 );
        }
        Integer bools = organizationalMapper.deleteFunc( func );
        return bools;
    }

    @Override
    public Integer insertFuncUrl( FuncUrl funcUrl ) {
        return organizationalMapper.insertFuncUrl( funcUrl );
    }

    @Override
    public Integer updateFuncUrl( FuncUrl funcUrl ) {
        return organizationalMapper.updateFuncUrl( funcUrl );
    }

    @Override
    public Integer deleteFuncUrl( FuncUrl funcUrl ) {
        return organizationalMapper.deleteFuncUrl( funcUrl );
    }

    @Override
    public List<FuncUrl> queryFuncUrlList( FuncUrl funcUrl ) {
        return organizationalMapper.queryFuncUrlList( funcUrl );
    }

    @Override
    public Integer queryFuncUrlListCount( FuncUrl funcUrl ) {
        return organizationalMapper.queryFuncUrlListCount( funcUrl );
    }

    @Override
    public FuncUrl queryFuncUrlWhere( FuncUrl funcUrl ) {
        return organizationalMapper.queryFuncUrlWhere( funcUrl );
    }

    @Override
    public List<Position> queryPositionTree( Position position ) {
        return organizationalMapper.queryPositionTree( position );
    }

    @Override
    public List<Menu> queryMenuJurisdiction() {
        return organizationalMapper.queryMenuJurisdiction();
    }

    @Override
    public List<Func> queryMenuFunction( Func func ) {
        return organizationalMapper.queryMenuFunction( func );
    }

    @Override
    public List<PositionFunc> queryPositionFunc( PositionFunc positionFunc ) {
        return organizationalMapper.queryPositionFunc( positionFunc );
    }

    @Override
    public Integer insertPositionFunc( List<PositionFunc> positionFuncs ) {
        PositionFunc positionFunc = new PositionFunc();
        positionFunc.setPosition_id( positionFuncs.get( 0 ).getPosition_id() );
        organizationalMapper.deletePositionFunc( positionFunc );
        for ( PositionFunc positionFunc1 : positionFuncs ) {
            if ( positionFunc1.getFunc_id() != null ) {
                organizationalMapper.insertPositionFunc( positionFunc1 );
            }
        }
        return 1;
    }

    @Override
    public List<Position> queryPositionToDept( Position position ) {
        return organizationalMapper.queryPositionToDept( position );
    }

    @Override
    public Integer queryPositionToDeptCount( Position position ) {
        return organizationalMapper.queryPositionToDeptCount( position );
    }

    //kevin
    @Override
    public Integer insertUser( User user ) {
        organizationalMapper.insertUser( user );
        Position position = new Position();
        position.setUser_id( user.getUser_id() );
        position.setPosition_id( user.getPosition_id() );
        position.setIs_primary( 1 );
        organizationalMapper.insertPositionToUser( position );
        return 1;
    }

    //kevin
    @Override
    public Integer updateUser( User user ) {
        return organizationalMapper.updateUser( user );
    }

    //kevin
    @Override
    public Integer deleteUser( User user ) {
        Position position = new Position();
        position.setUser_id( user.getUser_id() );
        organizationalMapper.deletePositionToUser( position );
        return organizationalMapper.deleteUser( user );
    }

    @Override
    public User queryLoginPassword( User user ) {
        return organizationalMapper.queryLoginPassword( user );
    }

    @Override
    public List<Menu> queryUserMunu( Menu menu ) {
        return organizationalMapper.queryUserMunu( menu );
    }

    @Override
    public Integer insertIcon( Icon icon ) {
        return organizationalMapper.insertIcon( icon );
    }

    @Override
    public List<Icon> queryIcon() {
        return organizationalMapper.queryIcon();
    }

    //kevin
    @Override
    public List<Dept> queryUserDept( String user_id ) {
        return organizationalMapper.queryUserDept( user_id );
    }

    //kevin
    @Override
    public List<User> queryDeptUser( String dept_id ) {
        return organizationalMapper.queryDeptUser( dept_id );
    }

}
