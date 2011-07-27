<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Piclets Test Page</title>
    </head>
    <body>
        
        <h2>Piclets Test Page</h2>
        
        <p>
        <img src='/piclet/bar.piclet?name=jou&ratio=0'  /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=10' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=20' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=30' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=40' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=50' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=60' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=70' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=80' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=90' /><br/><br/>
        <img src='/piclet/bar.piclet?name=jou&ratio=100'/><br/><br/>
        </p>
        
        <table>
        	<tr>
        		<td>seconds bar:</td>
        		<td><img src='/piclet/seconds-bar.piclet?name=seconds'/></td>
        	</tr>
        	<tr>
        		<td>session bar:</td> 
        		<td><img src='/piclet/seconds-session-bar.piclet?name=seconds'/></td>
        	</tr>
        	<tr>
        		<td>singleton bar:</td>
        		<td><img src='/piclet/seconds-singleton-bar.piclet?name=seconds'/></td>
        	</tr>
        </table>
        <br/><br/>
        
        <a href="/piclet/bar.piclet?name=jou50&ratio=50">download 50% bar</a><br/><br/>
        <a href="/piclet/bar.piclet?ratio=50">download autonamed 50% bar</a><br/><br/>
        
        
        <form action="/piclet/bar.piclet" method="get"> 
        	<input type="hidden" name="name" value="jou75" />
        	<input type="hidden" name="ratio" value="75" />
        	<input type="submit" value="download 75% bar (get)" />
        </form>
        
        <form action="/piclet/bar.piclet" method="post">
        	<input type="hidden" name="name" value="jou75" />
        	<input type="hidden" name="ratio" value="75" />
        	<input type="submit" value="download 75% bar (post)" />
        </form>
        
    </body>
</html>
