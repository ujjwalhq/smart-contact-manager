async function viewContact(contactId) {

    try {

        const response = await fetch(`/api/contacts/${contactId}`);

        if (!response.ok) {
            throw new Error("Unable to fetch contact");
        }

        const contact = await response.json();

        Swal.fire({

            width: window.innerWidth < 640 ? "98%" : "650px",

            background: "#111827",

            color: "#fff",

            showConfirmButton: false,

            showCloseButton: true,

            padding: window.innerWidth < 640 ? "1.2rem" : "2rem",

            customClass: {
                popup: "rounded-2xl"
            },

            html: `

            <div style="text-align:center;">

                <!-- Profile Image -->
                <img
                    src="${contact.picture}"
                    style="
                        width:90px;
                        height:90px;
                        border-radius:50%;
                        object-fit:cover;
                        border:4px solid #1447E6;
                        margin:auto;
                    ">

                <!-- Name -->

                <h2 style="
                    margin-top:15px;
                    font-size:32px;
                    font-weight:700;
                    color:#fff;
                ">
                    ${contact.name}
                </h2>

                <!-- Address -->

                <p style="
                    margin-top:4px;
                    color:#9CA3AF;
                    font-size:15px;
                ">
                    <i class="fa-solid fa-location-dot"></i>
                    ${contact.address ?? "No Address"}
                </p>

                <hr style="
                    margin:22px 0;
                    border-color:#374151;
                    opacity:.5;
                ">

                <!-- Email Card -->

                <div style="
                    background:#1F2937;
                    border-radius:14px;
                    padding:14px 18px;
                    margin-bottom:14px;
                    text-align:left;
                ">

                    <div style="
                        color:#9CA3AF;
                        font-size:12px;
                        font-weight:600;
                    ">

                        <i class="fa-solid fa-envelope"
                           style="color:#38BDF8;"></i>

                        Email

                    </div>

                    <div style="
                        margin-top:6px;
                        font-size:17px;
                        color:white;
                        word-break:break-all;
                    ">
                        ${contact.email}
                    </div>

                </div>

                <!-- Phone Card -->

                <div style="
                    background:#1F2937;
                    border-radius:14px;
                    padding:14px 18px;
                    margin-bottom:14px;
                    text-align:left;
                ">

                    <div style="
                        color:#9CA3AF;
                        font-size:12px;
                        font-weight:600;
                    ">

                        <i class="fa-solid fa-phone"
                           style="color:#22C55E;"></i>

                        Phone

                    </div>

                    <div style="
                        margin-top:6px;
                        font-size:17px;
                        color:white;
                    ">
                        ${contact.phoneNumber}
                    </div>

                </div>

                <!-- Description -->

                <div style="
                    background:#1F2937;
                    border-radius:14px;
                    padding:14px 18px;
                    text-align:left;
                ">

                    <div style="
                        color:#9CA3AF;
                        font-size:12px;
                        font-weight:600;
                    ">

                        <i class="fa-solid fa-circle-info"
                           style="color:#FACC15;"></i>

                        Description

                    </div>

                    <div style="
                        margin-top:6px;
                        color:white;
                        font-size:15px;
                        line-height:1.5;
                        word-break:break-word;
                    ">

                        ${contact.description || "No description available"}

                    </div>

                </div>

                <!-- Social Icons -->

                <div style="
                    display:flex;
                    justify-content:center;
                    align-items:center;
                    gap:42px;
                    margin-top:28px;
                    font-size:28px;
                ">

                    <a href="mailto:${contact.email}"
                       style="color:#38BDF8;text-decoration:none;">

                        <i class="fa-solid fa-envelope"></i>

                    </a>

                    <a href="https://wa.me/91${contact.phoneNumber}"
                       target="_blank"
                       style="color:#22C55E;text-decoration:none;">

                        <i class="fa-brands fa-whatsapp"></i>

                    </a>

                    ${contact.linkedInLink ?

                        `<a href="${contact.linkedInLink}"
                            target="_blank"
                            style="color:#0A66C2;text-decoration:none;">

                            <i class="fa-brands fa-linkedin"></i>

                        </a>`

                        : ""
                    }

                    ${contact.websiteLink ?

                        `<a href="${contact.websiteLink}"
                            target="_blank"
                            style="color:#D1D5DB;text-decoration:none;">

                            <i class="fa-solid fa-globe"></i>

                        </a>`

                        : ""
                    }

                </div>

            </div>

            `

        });

    } catch (error) {

        console.error(error);

        Swal.fire({

            icon: "error",

            title: "Oops...",

            text: "Unable to load contact details."

        });

    }

}