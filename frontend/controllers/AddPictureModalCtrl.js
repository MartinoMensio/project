var app = angular.module('App');

app.controller('AddPictureModalCtrl', ['$scope', '$uibModalInstance', 'ImagesService', function ($scope, $uibModalInstance, ImagesService) {

    // all this stuff because ng-change does not work on input type="file"
    this.inputField = null;

    this.save = () => {
        $uibModalInstance.close(this.image);
    };

    this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };

    this.openFileSelector = () => {
        // the pretty button has been clicked. Propagate the click to the input type="file"
        this.inputField = $("input[type=file]");
        this.inputField.click();
        this.inputField.on('change', this.changedFile);
    };

    this.changedFile = () => {
        // the selected image has been changed
        ImagesService.previewFile(this.inputField[0].files[0]).then((previewResult) => {
            this.image = previewResult;
        }, function (error) {
            // TODO do something
            this.image = null;
            console.log('error previewing the file');
        })
    }

}]);