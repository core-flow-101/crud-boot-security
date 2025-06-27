const editModal = document.getElementById('editModal')
editModal.addEventListener('show.bs.modal', function (event) {
    const btn = event.relatedTarget


    const id        = btn.dataset.userId
    const username  = btn.dataset.userUsername
    const surname   = btn.dataset.userSurname
    const middle    = btn.dataset.userMiddleName
    const birthday  = btn.dataset.userBirthday
    const email     = btn.dataset.userEmail
    const department= btn.dataset.userDepartment
    const photoB64  = btn.dataset.userPhoto


    document.getElementById('editId').value           = id
    document.getElementById('editFirstName').value    = username
    document.getElementById('editLastName').value     = surname
    document.getElementById('editMiddleName').value   = middle
    document.getElementById('editBirthday').value     = birthday
    document.getElementById('editEmail').value        = email
    document.getElementById('editDepartment').value  = department

    const img = document.getElementById('userPhotoPreview')
    if (photoB64) {
        img.src = `data:image/jpeg;base64,${photoB64}`
    } else {
        img.src = '/images/user-avo.png'
    }
})