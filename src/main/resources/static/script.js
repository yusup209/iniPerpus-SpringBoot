// Get data from localStorage or initialize empty arrays
let users = [];
let books = [];
let loans = [];

const api = {
  users: '/api/users',
  books: '/api/books',
  lendings: '/api/lendings'
};

// User Management
async function addUser(e) {
  e.preventDefault();
  const name = document.getElementById('userName').value;
  const email = document.getElementById('userEmail').value;
  const phone = document.getElementById('userPhone').value;

  const payload = { username: email, fullName: name, password: 'password' };
  await fetch(api.users, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
  await fetchUsers();
  e.target.reset();
}

function renderUsers() {
  const tbody = document.getElementById('usersBody');
  if (!tbody) return;

  tbody.innerHTML = users.map(u => `
    <tr>
      <td>${u.fullName}</td>
      <td>${u.username}</td>
      <td>—</td>
      <td><button class="btn-delete" onclick="deleteUser(${u.id})">Delete</button></td>
    </tr>
  `).join('');
}

async function deleteUser(id) {
  await fetch(`${api.users}/${id}`, { method: 'DELETE' });
  await fetchUsers();
}

async function fetchUsers() {
  const resp = await fetch(api.users);
  users = await resp.json();
  renderUsers();
  updateLendingDropdowns();
}

// Book Management
async function addBook(e) {
  e.preventDefault();
  const title = document.getElementById('bookTitle').value;
  const author = document.getElementById('bookAuthor').value;
  const isbn = document.getElementById('bookISBN').value;
  const copies = parseInt(document.getElementById('bookCopies').value);

  const payload = { title: title, author: author, isbn: isbn, copiesTotal: copies, copiesAvailable: copies };
  await fetch(api.books, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
  await fetchBooks();
  e.target.reset();
}

function renderBooks() {
  const tbody = document.getElementById('booksBody');
  if (!tbody) return;

  tbody.innerHTML = books.map(b => `
    <tr>
      <td>${b.title}</td>
      <td>${b.author}</td>
      <td>${b.isbn}</td>
      <td>${b.copiesAvailable || b.copiesAvailable === 0 ? b.copiesAvailable : b.availableCopies}/${b.copiesTotal || b.totalCopies}</td>
      <td><button class="btn-delete" onclick="deleteBook(${b.id})">Delete</button></td>
    </tr>
  `).join('');
}

async function deleteBook(id) {
  await fetch(`${api.books}/${id}`, { method: 'DELETE' });
  await fetchBooks();
}

async function fetchBooks() {
  const resp = await fetch(api.books);
  books = await resp.json();
  renderBooks();
  updateLendingDropdowns();
}

// Lending Management
function updateLendingDropdowns() {
  const userSelect = document.getElementById('lendUser');
  const bookSelect = document.getElementById('lendBook');

  if (!userSelect || !bookSelect) return;

  userSelect.innerHTML = '<option value="">Select a user</option>' +
    users.map(u => `<option value="${u.id}">${u.fullName || u.username}</option>`).join('');

  bookSelect.innerHTML = '<option value="">Select a book</option>' +
    books.filter(b => (b.copiesAvailable || b.copiesAvailable === 0 ? b.copiesAvailable : b.availableCopies) > 0)
      .map(b => `<option value="${b.id}">${b.title} (${b.copiesAvailable || b.availableCopies} available)</option>`).join('');
}

async function lendBook(e) {
  e.preventDefault();
  const userId = parseInt(document.getElementById('lendUser').value);
  const bookId = parseInt(document.getElementById('lendBook').value);
  const dueDate = document.getElementById('lendDueDate').value || null;

  const payload = { userId: userId, bookId: bookId, dueDate: dueDate };
  await fetch(api.lendings, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
  await fetchLoans();
  await fetchBooks();
  e.target.reset();
}

function renderLoans() {
  const tbody = document.getElementById('lendingBody');
  if (!tbody) return;

  tbody.innerHTML = loans.map(l => `
    <tr>
      <td>${l.borrower ? (l.borrower.fullName || l.borrower.username) : '—'}</td>
      <td>${l.book ? l.book.title : '—'}</td>
      <td>${l.dueDate ? l.dueDate : ''}</td>
      <td><span class="status-badge">${l.returnDate ? 'returned' : 'borrowed'}</span></td>
      <td>${l.returnDate ? '' : `<button class="btn-delete" onclick="returnBook(${l.id})">Return</button>`}</td>
    </tr>
  `).join('');
}

async function returnBook(loanId) {
  await fetch(`${api.lendings}/${loanId}/return`, { method: 'POST' });
  await fetchLoans();
  await fetchBooks();
}

async function fetchLoans() {
  const resp = await fetch(api.lendings);
  loans = await resp.json();
  renderLoans();
}

// Initial fetch
document.addEventListener('DOMContentLoaded', async () => {
  await fetchUsers();
  await fetchBooks();
  await fetchLoans();
});