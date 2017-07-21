var app = angular.module('App');

app.controller('EditProfileCtrl', ['UserService', '$state', 'user', 'profileAttributes', function (UserService, $state, user, profileAttributes) {
    // clone the user for the form
    this.user = angular.copy(user);
    // convert to Date object the string representation because angular would complain
    this.user.dateOfBirth = new Date(this.user.dateOfBirth);

    // copy the attribues for drop-down choices
    this.profileAttributes = profileAttributes;
    // if some select values are empty, initialize them to avoid angular to create an option without value
    this.user.carSharingService = this.user.carSharingService || this.profileAttributes.carSharingServices[0];
    this.user.educationLevel = this.user.educationLevel || this.profileAttributes.educationLevels[0];
    this.user.employment = this.user.employment || this.profileAttributes.employments[0];
    this.user.carFuel = this.user.CarFuel || this.profileAttributes.fuels[0];
    this.user.habitualTravelDocument = this.user.habitualTravelDocument || this.profileAttributes.travelDocuments[0];
    

    this.submit = () => {
        console.log('submit');
        UserService.updateProfile(this.user).then(function (result) {
            console.log('profile updated');
            $state.go('page.profile', null, { reload: true });
        })
    }
}]);