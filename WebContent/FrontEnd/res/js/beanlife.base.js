
//odd row change side
$(document).ready(function(){
  $('.row.mgb30.mgt20:odd').children('div:first-child').addClass('col-sm-push-6')
  $('.row.mgb30.mgt20:odd').children('div:last-child').addClass('col-sm-pull-6')
});

// scrollbar
jQuery(document).ready(function(){
    jQuery('.scrollbar-macosx').scrollbar();
});

/* Number only text */
$(document).ready(function(){
	var textValTmp="";
	$('.textNumOnly').keydown(function(e){
		textValTmp = $(this).val();
	});
	$('.textNumOnly').keyup(function(e){
		if(isNaN(Number($(this).val()))){
			console.log(textValTmp);
			$(this).val(textValTmp);
			textValTmp="";
		}
	});
	$('.textNumOnly').keypress(function(e){
		if(isNaN(Number($(this).val()))){
			$(this).val(textValTmp);
			textValTmp="";
		}
	});
});


var isAdd = false;

//$(document).ready(function () {
// $("a[href='#prod${param.prodNo}']").click();
//// });