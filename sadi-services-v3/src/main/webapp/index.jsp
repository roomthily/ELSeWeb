<%@ page contentType="text/html; charset=iso-8859-1" language="java"  %>
<%
  String hostName = request.getServerName();
%>
<?xml version='1.0' encoding='UTF-8'?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <title>SADI services at <%=hostName%></title>
    <link rel="icon" href="http://sadiframework.org/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="http://sadiframework.org/style/new.css">
  </head>
  <body>
    <div id='outer-frame'>
      <div id='inner-frame'>
        <div id='header'>
          <h1><a href="http://sadiframework.org/">SADI</a> services at <%=hostName%></h1>
        </div>
        <div id='nav'>
          <ul>
            <li class="page_item current_page_item">Services</li>
          </ul>
        </div>
        <div id='content'>
          <h2>SADI Services</h2>
	      <ul>
            <li><a href="./FractionalSnowCover06182002">FractionalSnowCover06182002</a></li>
            <li><a href="./WCSPayloadExtractor1">WCSPayloadExtractor1</a></li>
            <li><a href="./WCSPayloadExtractor2">WCSPayloadExtractor2</a></li>
            <li><a href="./WCSPayloadExtractor3">WCSPayloadExtractor3</a></li>
            <li><a href="./WCSPayloadExtractor4">WCSPayloadExtractor4</a></li>
            <li><a href="./WCSPayloadExtractor5">WCSPayloadExtractor5</a></li>
            <li><a href="./FractionalSnowCover07292002">FractionalSnowCover07292002</a></li>
            <li><a href="./Lifemapper">Lifemapper</a></li>
            <li><a href="./MinimumTemperatureNormals_December_1981_2010">MinimumTemperatureNormals_December_1981_2010</a></li>
            <li><a href="./FractionalSnowCover07122002">FractionalSnowCover07122002</a></li>
            <li><a href="./FractionalSnowCover07132002">FractionalSnowCover07132002</a></li>
	      </ul>
        </div> <!-- content -->
        <div id='footer'>
        </div> <!-- footer -->
      </div> <!-- inner-frame -->
    </div> <!-- outer-frame -->
  </body>
</html>