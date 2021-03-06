package com.example.finger.dao;

import com.example.finger.bean.FingerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 用户访问类;
 */
@Component(value = "fingerUserDao")
public interface FingerUserDao extends JpaRepository<FingerUser,Long> {

    @Query("select new FingerUser(f.id,f.name,f.status,f.updateDate) from FingerUser f")
    List<FingerUser> getAll();

    @Query("select new FingerUser(f.id,f.name,f.status,f.updateDate) from FingerUser f where f.name like %:search% ")
    List<FingerUser> getSearch(@Param("search")String search);

    FingerUser findById(long id);

    @Modifying
    @Query("update FingerUser f set f.status = :status , f.updateDate = :date where f.id = :fId")
    void editStatus(@Param("fId")long fId, @Param("status")long status , @Param("date") Date date);


    /**
     * 以下是jobs
     */
    @Query("select new FingerUser(f.id,f.name,s.title,f.jobsUpdateDate) from FingerUser f,FingerStatus s where f.fsId = s.id and f.status = 1 order by f.id")
    List<FingerUser> getFingerJobsAll();

    @Modifying
    @Query("update FingerUser f set f.fsId = :fsId , f.jobsUpdateDate = :date where f.id = :fId")
    void jobsEditStatus(@Param("fId")long fId, @Param("fsId")long fsId, @Param("date") Date date);

}
