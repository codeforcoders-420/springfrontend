document.addEventListener("DOMContentLoaded", function () {
    fetch('/proxy/stories')
        .then(response => response.json())
        .then(data => {
            const rgStoriesList = document.querySelector('.story-box ul');
            rgStoriesList.innerHTML = ''; // Clear existing items

            data.forEach(story => {
                const listItem = document.createElement('li');
                const link = document.createElement('a');
                link.href = `/details?story=${story.id}`; // Use actual story ID or URL field from the data
                link.textContent = story.title || story.id; // Use title or ID depending on the data
                listItem.appendChild(link);
                rgStoriesList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching stories:', error));
});