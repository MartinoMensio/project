var app = angular.module('App');

app.factory('ImagesService', ['$q', function ($q) {
    function previewFile(file) {
        var deferred = $q.defer();

        var reader = new FileReader();
        // check MIME type
        if (file.type.match(/^image/)) {
            reader.addEventListener("load", function () {
                // check MIME type also after preview
                if (reader.result.match(/^data:image\//)) {
                    deferred.resolve(reader.result);
                } else {
                    deferred.reject('Invalid type of file!');
                }
            }, false);

            if (file) {
                reader.readAsDataURL(file);
            }
        } else {
            deferred.reject('Invalid type of file!');
        }
        return deferred.promise;
    }
    return {
        previewFile: previewFile
    };
}]);