package com.pf.aforo.data.model

class DataUser (var type: String,
                var id: String,
                var firstName: String,
                var lastName: String,
                var email: String,
                var identificationNumber: String?,
                var phoneNumber: String,
                var refOrganization: String,
                var password: String,
                var role: String,
                var organizationOwner: Boolean,
                var refBranchOffice: String?) {
}
