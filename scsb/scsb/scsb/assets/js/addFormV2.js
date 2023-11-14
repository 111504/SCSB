$(document).ready(function() {
    // SmartWizard initialize
    $('#smartwizard').smartWizard({
  selected: 0, // Initial selected step, 0 = first step
  theme: 'round', // theme for the wizard, related css need to include for other than default theme
  justified: true, // Nav menu justification. true/false
  autoAdjustHeight: true, // Automatically adjust content height
  backButtonSupport: true, // Enable the back button support
  enableUrlHash: true, // Enable selection of the step based on url hash
  transition: {
      animation: 'none', // Animation effect on navigation, none|fade|slideHorizontal|slideVertical|slideSwing|css(Animation CSS class also need to specify)
      speed: '400', // Animation speed. Not used if animation is 'css'
      easing: '', // Animation easing. Not supported without a jQuery easing plugin. Not used if animation is 'css'
      prefixCss: '', // Only used if animation is 'css'. Animation CSS prefix
      fwdShowCss: '', // Only used if animation is 'css'. Step show Animation CSS on forward direction
      fwdHideCss: '', // Only used if animation is 'css'. Step hide Animation CSS on forward direction
      bckShowCss: '', // Only used if animation is 'css'. Step show Animation CSS on backward direction
      bckHideCss: '', // Only used if animation is 'css'. Step hide Animation CSS on backward direction
  },
  toolbar: {
      position: 'both', // none|top|bottom|both
      showNextButton: true, // show/hide a Next button
      showPreviousButton: true, // show/hide a Previous button
      extraHtml: '' // Extra html to show on toolbar
  },
  anchor: {
      enableNavigation: true, // Enable/Disable anchor navigation 
      enableNavigationAlways: false, // Activates all anchors clickable always
      enableDoneState: true, // Add done state on visited steps
      markPreviousStepsAsDone: true, // When a step selected by url hash, all previous steps are marked done
      unDoneOnBackNavigation: false, // While navigate back, done state will be cleared
      enableDoneStateNavigation: true // Enable/Disable the done state navigation
  },
  keyboard: {
      keyNavigation: true, // Enable/Disable keyboard navigation(left and right keys are used if enabled)
      keyLeft: [37], // Left key code
      keyRight: [39] // Right key code
  },
  lang: { // Language variables for button
      next: '下一步',
      previous: '返回'
  },
  disabledSteps: [], // Array Steps disabled
  errorSteps: [], // Array Steps error
  warningSteps: [], // Array Steps warning
  hiddenSteps: [], // Hidden steps
  getContent: null // Callback function for content loading
});

});

<!-- form io page 1 -->
Formio.createForm(document.getElementById('formiopage1'), 'https://run.mocky.io/v3/a4cda3a8-08a5-4d1d-8351-9032a9165a63', {
  readOnly: false
});
<!-- form io page 2 -->

Formio.createForm(document.getElementById('formiopage2'), 'https://run.mocky.io/v3/fb236ce0-4145-43bc-add5-10ded0bb418e', {
  readOnly: false
});

<!-- form io page 3 -->

Formio.createForm(document.getElementById('formiopage3'), 'https://run.mocky.io/v3/8251819c-e008-459e-8562-e31fac173d27', {
  readOnly: false
});

<!-- form io page 4 -->

Formio.createForm(document.getElementById('formiopage4'), 'https://run.mocky.io/v3/13c7ae6f-a773-465d-8978-d10cdf08324b', {
  readOnly: false
});