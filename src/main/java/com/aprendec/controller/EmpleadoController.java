package com.aprendec.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface EmpleadoController {
	void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

	void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
