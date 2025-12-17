package com.baddingSystem.entityRoles;

public enum Role {

	NORMAL ,  // it is allow your own services (limited service )
	MASTER,   // it is allow all service (it is allow NORMAL (admin) as well as own Services )
	USER      // It is allow only user related service not access admin services..
}
