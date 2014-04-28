document.write('<div id="left">'); // begin left
document.write('<ul id="globalnav">'); // begin globalnav
if (isSessionValid()) {
	document.write('<li><a href="index.html">Home</a></li>');
}
document.write('</ul><br>'); // end globalnav
document.write('</div>'); // end left
