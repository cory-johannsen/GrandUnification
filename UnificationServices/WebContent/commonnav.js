document.write('<div id="left">');
document.write('<ul id="globalnav">');
if (checkSessionCookie()) {
	document.write('<li><a href="index.html">Home</a></li>');
}
document.write('</ul><br>');
document.write('</div>');
