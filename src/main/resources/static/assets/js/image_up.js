const browseBtn = document.querySelector('.browse-btn');
const realInput = document.querySelector('#upload');

browseBtn.addEventListener('click',()=>{
	realInput.click();
});

var upload = document.querySelector('#upload');	 
 /* FileReader 객체 생성 */
var reader = new FileReader();
					 
/* reader 시작시 함수 구현 */
reader.onload = (function () {
										 
    this.image = document.createElement('img');
    var vm = this;
							
    return function (e) {
        /* base64 인코딩 된 스트링 데이터 */
	    vm.image.src = e.target.result
    }
})()
					 
upload.addEventListener('change',function (e) {
	var get_file = e.target.files;
						   
					 
	if(get_file){
		reader.readAsDataURL(get_file[0]);
	}
					 
	preview.appendChild(image);
})