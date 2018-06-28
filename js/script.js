/* Author:

*/

(function($){
	
	$("p.description").each(function(i,el){
		$("<a class='opener closed'>Read description +</a>").insertBefore(el);
		$(el).hide();
	});
	$(".opener").on("click", function(e){
		if ($(this).hasClass("closed")){
			$(this).html("Read description -").removeClass("closed").addClass("open").next("p").slideToggle();
		} else {
			$(this).html("Read description +").removeClass("open").addClass("closed").next("p").slideToggle();
		}
	});


})(jQuery);



