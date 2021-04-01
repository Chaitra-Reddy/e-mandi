package com.mindtree.EMandi.modules.clerk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mindtree.EMandi.modules.clerk.entity.Clerk;

@Repository
public interface ClerkRepository extends JpaRepository<Clerk, String> {

	@Query("select c.mandi.mandiPincode from Clerk c where c.clerkId = ?1")
	int findMandiPincodeByClerkId(String ClerkId);

}
