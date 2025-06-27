(function() {
    const table = document.getElementById('userTable');
    const tbody = table.tBodies[0];
    const originalRows = Array.from(tbody.querySelectorAll('tr'));
    let lastSortedColumn = null;

    window.sortTable = function(colIndex, isDate) {
        if (lastSortedColumn === colIndex) {
            tbody.innerHTML = '';
            originalRows.forEach(r => tbody.appendChild(r));
            lastSortedColumn = null;
            return;
        }

        const rows = Array.from(tbody.querySelectorAll('tr'));
        rows.sort((rowA, rowB) => {
            let a = rowA.cells[colIndex].innerText.trim();
            let b = rowB.cells[colIndex].innerText.trim();

            if (isDate) {
                const dateA = new Date(a);
                const dateB = new Date(b);

                return dateA - dateB;
            }
            return a.localeCompare(b, undefined, {numeric: true, sensitivity: 'base'});
        });

        tbody.innerHTML = '';
        rows.forEach(r => tbody.appendChild(r));

        lastSortedColumn = colIndex;
    }
})();