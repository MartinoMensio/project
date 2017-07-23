(function (window) {
  window.__env = window.__env || {};

  // backend url
  window.__env.backend = 'https://localhost:8888/';

  // geoservices url
  window.__env.geoservices = 'https://localhost:9999/';

  // Whether or not to enable debug mode
  // Setting this to false will disable console output
  window.__env.enableDebug = true;
}(this));