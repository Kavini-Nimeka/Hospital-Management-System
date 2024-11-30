const params = new URLSearchParams(window.location.search);
const id = params.get('id');
const url = `http://localhost:8085/api/v1/item/find?id=${id}`;
const getItem = async () => {
    if (!id) {
        return;
    }
    const res = await fetch(url);
    const item = await res.json();
        console.log(item)
    document.getElementById("input-item-name").value = item.name;
    document.getElementById("input-item-price").value = item.price;
    document.getElementById("save-new-item-btn").innerText = "Update Item";
}



document.getElementById("save-new-item-btn").addEventListener('click', async () => {
    let name = document.getElementById("input-item-name").value;
    let price = document.getElementById("input-item-price").value;
    if (name === "" ||  price === "") {
        alert("Please fill all the fields");
        return;
    }
    price= parseFloat(price)

    if (!id) {
        let patient = {name, price};
        const res = await fetch('http://localhost:8085/api/v1/item', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(patient)
        });
        if (!res.ok) {
            alert('Network response was not ok');
        }
        alert('Item added successfully');
        window.location.href = "item-list.html";
    } else {
        let patient = {id,name,  price};
        const res = await fetch(`http://localhost:8085/api/v1/item`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(patient)
        });
        if (!res.ok) {
            alert('Network response was not ok');
        }
        alert('Item updated successfully');
        window.location.href = "item-list.html";
    }
})

authAdmin = () => {
    const user = JSON.parse(localStorage.getItem('admin'));
    if (!user) {
        window.location.href = 'login.html';
    }
    getItem()
}
authAdmin();
