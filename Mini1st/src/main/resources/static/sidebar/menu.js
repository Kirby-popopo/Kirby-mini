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