"use strict";
(function () {
$(document).ready(function() {
    let pageSize = $('.product-list').data('pagesize');
    let pagePath = $('.product-list').data('pagepath');
    let apiURL=pagePath+'.sample.json';


 getProductDetails(pageSize,apiURL);

    	$("#loadMore").on('click', function (e) {

							$(".list-component:hidden").slice(0, pageSize).slideDown();
							if ($(".list-component:hidden").length == 0) {
							$("#loadMore").fadeOut('slow');
													}
					});


         })


})();

function getProductDetails(pageSize,apiURL){


  $.ajax({ 
        url:apiURL,
        type: 'GET',  
        dataType: "json",


        success:function(data) {
            //alert(data);
          const dataSet=data;
          console.log(dataSet);
         const size=dataSet.products.length;


          for(let objIndex = 0; objIndex < size; objIndex++){

				const image=dataSet.products[objIndex]['thumbnail'];
				const title=dataSet.products[objIndex]['title'];
				const desc=dataSet.products[objIndex]['description'];
 
           $('.productlisting_container').append($('<div class="col-xs-12 col-sm-6 col-md-4 productlist__item list-component" ><div class="item"><img src="' + image + '"' +' width="200" height="250"></img><div class="itemTxt"><div class="itemCategory"><a href="/">' + title +' </a></div><h2>' + desc +'</h2></div></div></div>'));


			$('.productlist__item').hide();
			$('.productlist__item:lt('+pageSize+')').show();

        }
        },
        error: function (response) {
        console.log(response.responseText);
		  $('#loadMore').addClass('hidden');
		  $('.errorblock').append(`<div class='apierror'>API Not Responding</p>`);
         }



       });


}



