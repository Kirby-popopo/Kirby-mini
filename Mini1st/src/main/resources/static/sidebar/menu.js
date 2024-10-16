        <!-- 알림, 검색 관련 JS -->
        function showContent(contentId) {
           const contents = document.querySelectorAll('.menu-section');
           const targetContent = document.getElementById(contentId);

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
                            search_section.innerHTML += `<a href="http://localhost:8090/${user.name}">
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
       document.addEventListener('click', function(event) {
          const menu = document.querySelector('.menu');
          const isClickInsideMenu = menu.contains(event.target);

       if (!isClickInsideMenu) {
           const activeSection = document.querySelector('.menu-section.active');
               if (activeSection) {
                  activeSection.classList.remove('active');
               }
           }
       });