function previewFile() {
	var preview = $('#img-preview')[0];
	var file = $('input[type=file]')[0].files[0];
	var reader = new FileReader();
	
	// check MIME type
	if (file.type.match(/^image/)) {
		reader.addEventListener("load", function() {
			// check MIME type also after preview
			if (reader.result.match(/^data:image\//)) {
				preview.src = reader.result;
			} else {
				$('#sendForm')[0].reset();
				preview.setAttribute('src', "");
				// display an error
				alert('Invalid type of file!');
			}
		}, false);
	
		if (file) {
			reader.readAsDataURL(file);
		}
	} else {
		$('#sendForm')[0].reset();
		preview.setAttribute('src', "");
		alert('Invalid type of file!');
	}
}

$(function() {
	$("input[type=file]").change(function(e) {
		previewFile();
	});
	$("#fake-attach").click(function(e) {
		$("input[type=file]").click();
	});
});