let itemList = [];

const getAllPatients = async () => {
    await fetch('http://localhost:8085/api/v1/item')
        .then((res) => res.json())
        .then((data) => {
            itemList = data;
        })
        .catch((err) => {
            console.log(err);
        })
}

const deleteItem = async (id) => {
    if (confirm("Are you sure you want to delete this Item?")) {
        await fetch(`http://localhost:8085/api/v1/item?id=${id}`, {
            method: 'DELETE',
        })
            .then((res) => {
                if (res.ok) {
                    alert("Deleted Successfully");
                    loadAllItem();
                }
            })
            .catch((err) => {
                console.log(err);
                alert('Something went wrong');
            })
    }
}
const loadAllItem = async () => {
    await getAllPatients();
    document.getElementById("item-list-table").innerHTML = '';
    itemList.forEach(item => {
        document.getElementById("item-list-table").innerHTML += `
            <tr id="${item.id}">
                <td>
                    <h2 class="table-avatar">
                            <a href="../profile.html">#000-${item.id}</a>
                    </h2>
                </td>
                <td>${item.name}</td>

                <td class="text-right">${item.price}.00LKR</td>
                <td className="col-auto profile-btn">
             
                    <a href="edit-item.html?id=${item.id}" className="btn btn-primary">
                        Edit
                    </a>
                    /   
                    <a onclick="deleteItem(${item.id})" class="btn text-danger">
                        Delete
                    </a>
                </td>
            </tr>
            `
    })
}
loadAllItem();
