var app = angular.module('App');

app.controller('ProfileCtrl', ['ImagesService', '$uibModal', '$state', '$rootScope', function (ImagesService, $uibModal, $state, $rootScope) {

    this.openAddPictureModal = function () {
        var modalInstance = $uibModal.open({
            templateUrl: 'templates/modals/addPictureModal.html',
            controller: 'AddPictureModalCtrl',
            controllerAs: 'ctrl',
            size: 'lg'
        });

        modalInstance.result.then(function (image) {
            ImagesService.updateUserImage(image).then(function (result) {
                console.log('profile picture updated');
                $state.go('page.profile', null, { reload: true });
            }, function (error) {
                $rootScope.$emit('error', {message: "error updating profile picture"})
            })
        }, function () {
            console.log('Modal dismissed at: ' + new Date());
        });
    };
}]);