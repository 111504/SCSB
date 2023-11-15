(function() {
  "use strict"; // Start of use strict

  var sidebar = document.querySelector('.sidebar');
  var sidebarToggles = document.querySelectorAll('#sidebarToggle1, #sidebarToggleTop1');
  
  if (sidebar) {
    
    var collapseEl = sidebar.querySelector('.collapse');
    var collapseElementList = [].slice.call(document.querySelectorAll('.sidebar .collapse'))
    var sidebarCollapseList = collapseElementList.map(function (collapseEl) {
      return new bootstrap.Collapse(collapseEl, { toggle: false });
    });

    for (var toggle of sidebarToggles) {

      // Toggle the side navigation
      toggle.addEventListener('click', function(e) {
        document.body.classList.toggle('sidebar-toggled');
        sidebar.classList.toggle('toggled');

        if (sidebar.classList.contains('toggled')) {
          for (var bsCollapse of sidebarCollapseList) {
            bsCollapse.hide(); 
          }
        };
      });
    }

    // Close any open menu accordions when window is resized below 768px
    window.addEventListener('resize', function() {
      var vw = Math.max(document.documentElement.clientWidth || 0, window.innerWidth || 0);

      if (vw < 768) {
        for (var bsCollapse of sidebarCollapseList) {
          bsCollapse.hide();
        }
      };
    });
  }

  // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
  
  var fixedNaigation = document.querySelector('body.fixed-nav .sidebar');
  
  if (fixedNaigation) {
    fixedNaigation.on('mousewheel DOMMouseScroll wheel', function(e) {
      var vw = Math.max(document.documentElement.clientWidth || 0, window.innerWidth || 0);

      if (vw > 768) {
        var e0 = e.originalEvent,
          delta = e0.wheelDelta || -e0.detail;
        this.scrollTop += (delta < 0 ? 1 : -1) * 30;
        e.preventDefault();
      }
    });
  }

  var scrollToTop = document.querySelector('.scroll-to-top');
  
  if (scrollToTop) {
    
    // Scroll to top button appear
    window.addEventListener('scroll', function() {
      var scrollDistance = window.pageYOffset;

      //check if user is scrolling up
      if (scrollDistance > 100) {
        scrollToTop.style.display = 'block';
      } else {
        scrollToTop.style.display = 'none';
      }
    });
  }

})(); // End of use strict

//time
$(document).ready(function () {
  function updateClock() {
    var now = moment().format('YYYY年MM月DD日 HH:mm:ss');
    $('#nowtime').text(now);
  }
  updateClock();

  setInterval(updateClock, 1000);
});
let currentLoginUserIndex = document.getElementById('currentLoginUser').textContent;


document.addEventListener('DOMContentLoaded', function() {

  $.ajax({
    url: 'http://localhost:8888/api/user/pending-records?empId=' + currentLoginUserIndex,
    method: 'GET',
    success: function(data) {
      let recordCount = data.data.length;
      console.log(recordCount);

      let unresolvedCasesElement = document.querySelector('.unresolvedCases');
      unresolvedCasesElement.textContent = `${recordCount}件`;
    }
  });
  $.ajax({
    url: 'http://localhost:8888/api/user/personal-records?empId=' + currentLoginUserIndex,
    method: 'GET',
    success: function(data) {

      let recordCount = data.data.length;
      console.log(recordCount);

      let resolvedCasesElement = document.querySelector('.resolvedCases');
      resolvedCasesElement.textContent = `${recordCount}件`;
    }
  });

});