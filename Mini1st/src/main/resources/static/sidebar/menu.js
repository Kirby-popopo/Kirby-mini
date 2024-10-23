        <!-- 알림, 검색 관련 JS -->
        function showContent(contentId) {
           const contents = document.querySelectorAll('.menu-section');
           const targetContent = document.getElementById(contentId);

           if (contentId=='profile'){
                location.href = "/profile";
           }

           if (targetContent.classList.contains('active')) {
               targetContent.classList.remove('active');
               setTimeout(() => {
                   document.getElementById('home').classList.add('active');
               }, 300);
           } else {
               contents.forEach(content => content.classList.remove('active'));
               setTimeout(() => {
                   targetContent.classList.add('active');
               }, 10);
           }
       }

      <!-- searchbar change event -->
        function inputTextInputEvent() {
       		let xhr = new XMLHttpRequest();
       		let userId = document.getElementById("inputText").value;
       		xhr.onload = function() {
       			if (xhr.status == 200) {
       			    let search_section = document.querySelector(".search-section");
                    search_section.innerHTML = "";

                    if (xhr.responseText.length == 0){
                        return;
                    }

       			    let users = JSON.parse(xhr.responseText);

                    search_section.innerHTML = "";
                        users.forEach(user => {
                            search_section.innerHTML += `<a href="http://localhost:8090/profile/${user.user_id}">
                                                            <div class="search-item">
                                                                <img src="/placeholder.svg?height=30&width=30" alt="User">
                                                                <span>${user.name}</span>
                                                            </div>
                                                         </a>`;
                        });
       			    }
       		};
       		xhr.open('POST', '/SearchUser', true);
       		xhr.setRequestHeader('Content-Type', 'application/json');
       		xhr.send(JSON.stringify({ userId: userId }));
       	}

       <!-- 메뉴 영역 외부 클릭 감지 후 active 클래스 제거 -->
       document.addEventListener('DOMContentLoaded', function() {
         // sidebar.html 로드가 완료된 후 실행되도록 추가 설정
         fetch('/sidebar/sidebar.html')
             .then(response => response.text())
             .then(data => {
                 document.getElementById('sidebar-container').innerHTML = data;

                 // '만들기' 버튼 클릭 시 모달 열기 이벤트 등록
                 const createPostButton = document.getElementById('createPost');
                 const modalContainer = document.getElementById('modalContainer');

                 if (createPostButton && modalContainer) {
                     createPostButton.addEventListener('click', function() {
                         if (modalContainer.children.length > 0) {
                             // 이미 iframe이 있는 경우 모달을 보여줍니다.
                             modalContainer.style.display = 'block';
                         } else {
                             // 모달 iframe을 생성하여 'createPost.html'을 로드합니다.
                             const iframe = document.createElement('iframe');
                             iframe.src = 'createPost.html';
                             iframe.style.width = '100%';
                             iframe.style.height = '100%';
                             iframe.style.border = 'none';

                             modalContainer.appendChild(iframe);
                             modalContainer.style.display = 'block';

                             // iframe 내부의 모달 닫기 버튼 이벤트 등록
                             iframe.onload = function() {
                                 const iframeDocument = iframe.contentWindow.document;
                                 const closeModalButton = iframeDocument.getElementById('closeButton');
                                 if (closeModalButton) {
                                     closeModalButton.addEventListener('click', function() {
                                         modalContainer.style.display = 'none';
                                         // iframe 내용을 지우기 (리셋 효과)
                                         modalContainer.innerHTML = '';
                                     });
                                 }
                             };
                         }
                     });
                 }
             })
             .catch(error => console.error('Error loading sidebar:', error));
     });

