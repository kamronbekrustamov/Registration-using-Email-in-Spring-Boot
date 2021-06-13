<h2>Registration with Email Confirmation in Spring Boot with Spring Security</h2>

<h3>API EndPoints</h3>

<ul>
  <li>
    <h4>Registration</h4>
    <p>POST Request to http://localhost:8080/api/v1/userse/signup/</p>
    <p>Data Format: {"firstName": "firstName", "lastName": "lastName", "email": "email", "password": "password"}</p>
  </li>
  <li>
    <h4>Registration</h4>
    <p>GET Request to http://localhost:8080/api/v1/userse/confirm/?token="token"</p>
  </li>
</ul>

<h3>Email Service</h3>
<p>Email Service is maildev</p>
<p>You can get info in <a href="https://www.npmjs.com/package/maildev">here</p>

<h3>Additional Configs are given in application.yml and SecurityConfig.java files</h3>
