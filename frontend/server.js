var fs = require('fs'); 
var path = require('path'); 
var liveServer = require('live-server'); 
 
liveServer.start({ 
    port: process.env.PORT || 8080,
    open: false, 
    root: path.resolve(__dirname, 'app/') 
    // TODO certificate HTTPS 
});