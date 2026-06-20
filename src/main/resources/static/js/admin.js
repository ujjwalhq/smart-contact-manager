console.log("Admin JS Loaded");
document.querySelector("#image_file_input").addEventListener("change", function (event) {

    let file = event.target.files[0];

    let reader = new FileReader();

    reader.onload = function () {

        let preview = document.getElementById("upload_image_preview");

        preview.src = reader.result;
        preview.classList.remove("hidden");

    };

    reader.readAsDataURL(file);

});