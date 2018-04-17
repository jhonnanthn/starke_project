function truncate(text, count){
	text += "";
	while(text.length < count){
		text = "0" + text;
	}
	return text;
}