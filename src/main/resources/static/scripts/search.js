function applyFilters() {
    const text    = document.getElementById('searchInput').value.trim().toLowerCase();
    const role    = document.getElementById('roleFilter').value;
    const dept    = document.getElementById('deptFilter').value;
    const rows    = document.querySelectorAll('#userTable tbody tr');

    rows.forEach(row => {
        const firstName = row.cells[1].innerText.trim().toLowerCase();
        const department= row.cells[6].innerText.trim();

        const roles = Array.from(row.cells[7]
            .querySelectorAll('li'))
            .map(li => li.innerText.trim());

        const textMatch = !text || firstName.includes(text);

        const roleMatch = !role || roles.includes(role);

        const deptMatch = !dept || department === dept;

        row.style.display = (textMatch && roleMatch && deptMatch) ? '' : 'none';
    });
}