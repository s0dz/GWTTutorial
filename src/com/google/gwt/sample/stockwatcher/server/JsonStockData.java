package com.google.gwt.sample.stockwatcher.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This does the same pseudo calculation of stock prices as the client side Javascript
 * but instead on the server side using this servlet.
 * 
 * Returns a JSON payload.
 * 
 * Example usage: http://localhost:8888/stockwatcher/stockPrices?q=ABC+DEF
 * 
 * @author gmctear
 *
 */
public class JsonStockData extends HttpServlet {

	private static final long serialVersionUID = 8491800536020050397L;
	
	private static final double MAX_PRICE = 100.0; // $100.00
	private static final double MAX_PRICE_CHANGE = 0.02; // +/- 2%
	
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		
		Random random = new Random();
		
		PrintWriter out = response.getWriter();
		out.println('[');
		String[] stockSymbols = request.getParameter("q").split(" ");
		boolean firstSymbol = true;
		for( String stockSymbol : stockSymbols ) {
			
			double price = random.nextDouble() * MAX_PRICE;
			double change = price * MAX_PRICE_CHANGE * (random.nextDouble() * 2f - 1f );
			
			if( firstSymbol ) {
				firstSymbol = false;
			}
			else {
				out.println(" ,");
			}
			out.println("  {");
			out.print("    \"symbol\": \"");
			out.print(stockSymbol);
			out.println("\",");
			out.print("    \"price\": ");
			out.print(price);
			out.println(',');
			out.print("    \"change\": ");
			out.println(change);
			out.println("  }");
		}
		out.println(']');
	    out.flush();
	}
}
