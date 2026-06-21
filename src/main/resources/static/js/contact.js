console.log("Contact JS Loaded");


//delete contact

function deleteContact(contactId) {

    Swal.fire({
        title: "Delete Contact?",
        text: "This contact will be permanently removed.",
        icon: "warning",
        showCancelButton: true,
        background: "#111827",
        color: "#ffffff",
        confirmButtonColor: "#dc2626",
        cancelButtonColor: "#374151",
        confirmButtonText: "Yes, Delete",
        cancelButtonText: "Cancel"
    }).then((result) => {

        if (result.isConfirmed) {

            window.location.href = "/user/contacts/delete/" + contactId;

        }

    });

}


