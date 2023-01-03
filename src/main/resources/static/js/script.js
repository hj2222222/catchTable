// 주차정보 정보란
   function openCloseToc() {
    const a = document.querySelector('#toc-toggle')
   if(document.getElementById('toc-content').style.display === 'block') {
       document.getElementById('toc-content').style.display = 'none';
       document.getElementById('toc-toggle').textContent = '주차정보';
       a.classList.remove('active')

   } else {
       document.getElementById('toc-content').style.display = 'block';
       document.getElementById('toc-toggle').textContent = '주차정보';
       a.classList.add('active')
   }
   }


// <!-- 북마크 -->
const bmbtn = document.querySelector('.btn-bookmark')


$('.btn-bookmark').on({
    'click': function() {
         var src = ($(this).attr('src') === '../bookmark_1.svg')
            ? '../bookmark_2.svg' 
            : '../bookmark_1.svg';
         $(this).attr('src', src);
         var aaa = ($(this).attr('src') === '../bookmark_1.svg')
            ? toast('   저장한 레스토랑에서 삭제되었습니다       ') 
            : toast('   레스토랑이 저장되었습니다    편집하기 >');
    }
});


// 토스트 팝업
function fillWidth(elem, timer, limit) {
	if (!timer) { timer = 3000; }	
	if (!limit) { limit = 100; }
	var width = 1;
	var id = setInterval(frame, timer / 100);
		function frame() {
		if (width >= limit) {
			clearInterval(id);
		} else {
			width++;
			elem.style.width = width + '%';
		}
	}
};

function toast(msg, timer) {
	if (!timer) { timer = 3000; }
	var $elem = $("<div class='toastWrap'><span class='toast'>" + msg + "<b></b><div class='timerWrap'><div class='timer'></div></div></span></div>");
	$("#toast").append($elem); //top = prepend, bottom = append
	$elem.slideToggle(100, function() {
		$('.timerWrap', this).first().outerWidth($elem.find('.toast').first().outerWidth() - 10);
		if (!isNaN(timer)) {
			fillWidth($elem.find('.timer').first()[0], timer);
			setTimeout(function() {
				$elem.fadeOut(function() {
					$(this).remove();
				});
			}, timer);			
		}
	});
}

$("#toast").on("click", "b", function() {
	$(this).closest('.toastWrap').remove();
})




// 딤처리 레이어 팝업

$('#btn-example').click(function(){
    var $href = $(this).attr('href');
    layer_popup($href);
});
function layer_popup(el){

    var $el = $(el);    //레이어의 id를 $el 변수에 저장
    var isDim = $el.prev().hasClass('dimBg'); //dimmed 레이어를 감지하기 위한 boolean 변수

    isDim ? $('.dim-layer').fadeIn() : $el.fadeIn();

    var $elWidth = ~~($el.outerWidth()),
        $elHeight = ~~($el.outerHeight()),
        docWidth = $(document).width(),
        docHeight = $(document).height();

    // 화면의 중앙에 레이어를 띄운다.
    if ($elHeight < docHeight || $elWidth < docWidth) {
        $el.css({
            marginTop: -$elHeight /2,
            marginLeft: -$elWidth/2
        })
    } else {
        $el.css({top: 0, left: 0});
    }

    $el.find('a.btn-layerClose').click(function(){
        isDim ? $('.dim-layer').fadeOut() : $el.fadeOut(); // 닫기 버튼을 클릭하면 레이어가 닫힌다.
        return false;
    });

    $('.layer .dimBg').click(function(){
        $('.dim-layer').fadeOut();
        return false;
    });

}


// 팔로우 증가
function count(type)  {
    // 결과를 표시할 element
    const resultElement = document.getElementById('result');
    
    // 현재 화면에 표시된 값
    let number = resultElement.innerText;
    
    // 더하기/빼기
    if(type === 'plus') {
      number = parseInt(number) + 1;
    }else if(type === 'minus')  {
      number = parseInt(number) - 1;
    }
    
    // 결과 출력
    resultElement.innerText = number;
  }


//   좋아요
function addLike(){
    const pushHeartBtn = document.querySelector(".heartBtn");
            pushHeartBtn.innerHTML ='<i class="xi-heart xi-2x"></i>';
            pushHeartBtn.style.color ='red'; 
  			pushHeartBtn.addEventListener("click",countPlus);
}



