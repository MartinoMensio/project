(function (window) {
  window.__env = window.__env || {};

  // backend url
  window.__env.backend = 'https://ai-project-backend.herokuapp.com/';

  // geoservices url
  window.__env.geoservices = 'https://ai-project-geoservices.herokuapp.com/';

  // Whether or not to enable debug mode
  // Setting this to false will disable console output
  window.__env.enableDebug = true;
}(this));