package com.xg.hlh.automation_web.test.dao;
import com.xg.hlh.automation_web.test.JpaRepository.MyRepository;
import com.xg.hlh.automation_web.test.domain.AwardsRank;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardsRankRepository extends MyRepository<AwardsRank,Long> {

}