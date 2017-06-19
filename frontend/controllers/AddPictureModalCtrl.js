var app = angular.module('App');

app.controller('AddPictureModalCtrl', ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
    this.save = function () {
        $uibModalInstance.close($ctrl.selected.item);
    };

    this.cancel = function () {
        $uibModalInstance.dismiss('cancel');
    };
}]);