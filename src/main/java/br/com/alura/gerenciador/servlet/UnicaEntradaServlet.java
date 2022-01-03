package br.com.alura.gerenciador.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.acao.Acao;

//@WebServlet("/entrada")
public class UnicaEntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nomeDaClasse = "br.com.alura.gerenciador.acao." + request.getParameter("acao");

		String nome;
		try {
			Class<?> classe = Class.forName(nomeDaClasse);// carrega a classe com o nome
			Acao acao = (Acao) classe.getDeclaredConstructor().newInstance();
			nome = acao.executa(request, response);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new ServletException(e);
		}

		String[] tipoEEndereco = nome.split(":");
		if (tipoEEndereco[0].equals("forward")) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/" + tipoEEndereco[1]);
			rd.forward(request, response);
		} else {
			response.sendRedirect(tipoEEndereco[1]);
		}

	}

}
