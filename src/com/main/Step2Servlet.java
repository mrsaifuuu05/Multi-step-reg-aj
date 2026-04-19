package com.saif;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Step2Servlet
 * Problem 23 - Multi-Step Registration Form (Step 2)
 * Concept Practiced: HttpSession
 *
 * Retrieves Name and Email from the HttpSession (saved in Step 1),
 * reads Mobile Number and City from the current form submission,
 * then displays a complete registration summary of all four fields.
 *
 * Author: Saif
 */
@WebServlet("/Step2Servlet")
public class Step2Servlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ------------------------------------------------------------------ GET
    // Redirect any direct GET request back to Step 1
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    // ----------------------------------------------------------------- POST
    // Called when Step 2 form is submitted
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        /* ---- Step 1: Retrieve session data saved in Step 1 ---- */
        // false = do NOT create a new session; only get existing one
        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("name") == null
                || session.getAttribute("email") == null) {
            // Session expired or user skipped Step 1 directly
            sendError(out, "Session expired or Step 1 was skipped. Please start from Step 1.",
                      "index.html", "Go to Step 1");
            return;
        }

        // Retrieve name and email from session
        String name  = (String) session.getAttribute("name");
        String email = (String) session.getAttribute("email");

        /* ---- Step 2: Read Step 2 form parameters ---- */
        String mobile = request.getParameter("mobile");
        String city   = request.getParameter("city");

        /* ---- Step 3: Input Validation ---- */
        if (mobile == null || mobile.trim().isEmpty()
                || city == null || city.trim().isEmpty()) {
            sendError(out, "Mobile number and city are required. Please go back and fill them in.",
                      "javascript:history.back()", "Go Back");
            return;
        }

        mobile = mobile.trim();
        city   = city.trim();

        // Mobile must be exactly 10 digits
        if (!mobile.matches("\\d{10}")) {
            sendError(out, "Mobile number must be exactly 10 digits. Please go back and correct it.",
                      "javascript:history.back()", "Go Back");
            return;
        }

        /* ---- Step 4: Invalidate session after successful registration ---- */
        session.invalidate(); // clean up session data

        /* ---- Step 5: Display full registration summary of all 4 fields ---- */
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Registration Complete</title>");
        out.println("<style>");
        out.println("*{box-sizing:border-box;margin:0;padding:0}");
        out.println("body{font-family:'Segoe UI',sans-serif;background:linear-gradient(135deg,#1a1a2e,#16213e,#0f3460);min-height:100vh;display:flex;align-items:center;justify-content:center;padding:20px}");
        out.println(".card{background:#fff;border-radius:16px;box-shadow:0 20px 60px rgba(0,0,0,.3);padding:40px;width:100%;max-width:480px}");
        out.println(".header{text-align:center;margin-bottom:24px}");
        out.println(".icon{font-size:48px;display:block;margin-bottom:10px}");
        out.println("h1{color:#1a1a2e;font-size:22px;font-weight:700}");
        out.println(".sub{color:#666;font-size:13px;margin-top:4px}");
        out.println(".badge{background:#f0fff4;border:1px solid #28a745;border-radius:8px;padding:10px 16px;text-align:center;color:#1a7a35;font-weight:600;font-size:14px;margin-bottom:22px}");
        out.println(".box{background:#f8f9ff;border-radius:10px;overflow:hidden;margin-bottom:22px}");
        out.println(".box-hdr{background:#0f3460;color:#fff;padding:12px 18px;font-weight:600;font-size:14px}");
        out.println(".row{display:flex;padding:12px 18px;border-bottom:1px solid #eee;font-size:14px}");
        out.println(".row:last-child{border-bottom:none}");
        out.println(".lbl{color:#888;width:140px;flex-shrink:0;font-size:13px}");
        out.println(".val{color:#1a1a2e;font-weight:700}");
        out.println(".btn{display:block;text-align:center;padding:13px;background:linear-gradient(135deg,#0f3460,#533483);color:#fff;border-radius:8px;text-decoration:none;font-weight:600;font-size:15px}");
        out.println("</style></head><body>");
        out.println("<div class='card'>");
        out.println("  <div class='header'><span class='icon'>&#127881;</span>");
        out.println("  <h1>Registration Complete!</h1>");
        out.println("  <p class='sub'>All details have been saved successfully</p></div>");
        out.println("  <div class='badge'>&#10003; Registration Successful</div>");
        out.println("  <div class='box'>");
        out.println("    <div class='box-hdr'>&#128203; Registration Summary</div>");
        out.println("    <div class='row'><span class='lbl'>Full Name</span><span class='val'>"    + escapeHtml(name)   + "</span></div>");
        out.println("    <div class='row'><span class='lbl'>Email Address</span><span class='val'>" + escapeHtml(email)  + "</span></div>");
        out.println("    <div class='row'><span class='lbl'>Mobile Number</span><span class='val'>" + escapeHtml(mobile) + "</span></div>");
        out.println("    <div class='row'><span class='lbl'>City</span><span class='val'>"          + escapeHtml(city)   + "</span></div>");
        out.println("  </div>");
        out.println("  <a href='index.html' class='btn'>&#43; Register Another</a>");
        out.println("</div></body></html>");
    }

    /* ---- Utility: HTML escaping to prevent XSS ---- */
    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    /* ---- Utility: styled error page ---- */
    private void sendError(PrintWriter out, String message, String href, String btnText) {
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Error</title>");
        out.println("<style>body{font-family:sans-serif;display:flex;align-items:center;justify-content:center;min-height:100vh;margin:0;background:#1a1a2e}");
        out.println(".box{background:#fff;border-radius:12px;padding:36px;max-width:420px;text-align:center}");
        out.println("h2{color:#c0392b;margin-bottom:14px}p{color:#555;margin-bottom:20px}");
        out.println("a{color:#fff;background:#0f3460;padding:10px 24px;border-radius:8px;text-decoration:none;font-weight:600}</style></head><body>");
        out.println("<div class='box'><h2>&#9888; Error</h2><p>" + message + "</p>");
        out.println("<a href='" + href + "'>" + btnText + "</a></div></body></html>");
    }
}
