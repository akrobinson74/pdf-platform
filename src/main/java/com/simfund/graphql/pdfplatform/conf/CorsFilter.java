package com.simfund.graphql.pdfplatform.conf;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    final var resp = (HttpServletResponse) response;

    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS");
    resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
    resp.setHeader("Access-Control-Max-Age", "3600");

    if (((HttpServletRequest) request).getMethod().equalsIgnoreCase("OPTIONS")) {
      resp.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(request, response);
    }
  }
}
