package com.apps.nycschools.list.model

/*
 * Thoughts:
 * This model closely associated with the UI. Contains formatted data
 * that's suitable for View to just display versus SchoolListDto
 * which is a direct response of API and may not be suitable for UI
 */
/**
 * Basic details of the school.
 *
 * @param id dbn of the School. Used to get the full details of a School
 * @param name Full name of the School
 */
data class School(val id: String, val name: String)