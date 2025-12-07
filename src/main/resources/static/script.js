// Get data from localStorage or initialize empty arrays
let users = JSON.parse(localStorage.getItem('users')) || [];
let books = JSON.parse(localStorage.getItem('books')) || [];
let loans = JSON.parse(localStorage.getItem('loans')) || [];
let userIdCounter = parseInt(localStorage.getItem('userIdCounter')) || 1;
let bookIdCounter = parseInt(localStorage.getItem('bookIdCounter')) || 1;
let loanIdCounter = parseInt(localStorage.getItem('loanIdCounter')) || 1;

// Save data to localStorage
function saveData() {
  localStorage.setItem('users', JSON.stringify(users));
  localStorage.setItem('books', JSON.stringify(books));
  localStorage.setItem('loans', JSON.stringify(loans));
  localStorage.setItem('userIdCounter', userIdCounter);
  localStorage.setItem('bookIdCounter', bookIdCounter);
  localStorage.setItem('loanIdCounter', loanIdCounter);
}

// User Management
function addUser(e) {
  e.preventDefault();
  const user = {
    id: userIdCounter++,
    name: document.getElementById('userName').value,
    email: document.getElementById('userEmail').value,
    phone: document.getElementById('userPhone').value
  };
  users.push(user);
  saveData();
  renderUsers();
  e.target.reset();
}

function renderUsers() {
  const tbody = document.getElementById('usersBody');
  if (!tbody) return;
  
  tbody.innerHTML = users.map(u => `
    <tr>
      <td>${u.name}</td>
      <td>${u.email}</td>
      <td>${u.phone}</td>
      <td><button class="btn-delete" onclick="deleteUser(${u.id})">Delete</button></td>
    </tr>
  `).join('');
}

function deleteUser(id) {
  users = users.filter(u => u.id !== id);
  saveData();
  renderUsers();
}

// Book Management
function addBook(e) {
  e.preventDefault();
  const book = {
    id: bookIdCounter++,
    title: document.getElementById('bookTitle').value,
    author: document.getElementById('bookAuthor').value,
    isbn: document.getElementById('bookISBN').value,
    totalCopies: parseInt(document.getElementById('bookCopies').value),
    availableCopies: parseInt(document.getElementById('bookCopies').value)
  };
  books.push(book);
  saveData();
  renderBooks();
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
      <td>${b.availableCopies}/${b.totalCopies}</td>
      <td><button class="btn-delete" onclick="deleteBook(${b.id})">Delete</button></td>
    </tr>
  `).join('');
}

function deleteBook(id) {
  books = books.filter(b => b.id !== id);
  saveData();
  renderBooks();
}

// Lending Management
function updateLendingDropdowns() {
  const userSelect = document.getElementById('lendUser');
  const bookSelect = document.getElementById('lendBook');
  
  if (!userSelect || !bookSelect) return;
  
  userSelect.innerHTML = '<option value="">Select a user</option>' + 
    users.map(u => `<option value="${u.id}">${u.name}</option>`).join('');
  
  bookSelect.innerHTML = '<option value="">Select a book</option>' + 
    books.filter(b => b.availableCopies > 0)
      .map(b => `<option value="${b.id}">${b.title} (${b.availableCopies} available)</option>`).join('');
}

function lendBook(e) {
  e.preventDefault();
  const userId = parseInt(document.getElementById('lendUser').value);
  const bookId = parseInt(document.getElementById('lendBook').value);
  const dueDate = document.getElementById('lendDueDate').value;
  
  const user = users.find(u => u.id === userId);
  const book = books.find(b => b.id === bookId);
  
  if (book.availableCopies > 0) {
    book.availableCopies--;
    const loan = {
      id: loanIdCounter++,
      userId: userId,
      userName: user.name,
      bookId: bookId,
      bookTitle: book.title,
      dueDate: dueDate,
      status: 'borrowed'
    };
    loans.push(loan);
    saveData();
    renderLoans();
    renderBooks();
    updateLendingDropdowns();
    e.target.reset();
  }
}

function renderLoans() {
  const tbody = document.getElementById('lendingBody');
  if (!tbody) return;
  
  tbody.innerHTML = loans.map(l => `
    <tr>
      <td>${l.userName}</td>
      <td>${l.bookTitle}</td>
      <td>${l.dueDate}</td>
      <td><span class="status-badge status-${l.status}">${l.status}</span></td>
      <td><button class="btn-delete" onclick="returnBook(${l.id})">Return</button></td>
    </tr>
  `).join('');
}

function returnBook(loanId) {
  const loan = loans.find(l => l.id === loanId);
  const book = books.find(b => b.id === loan.bookId);
  book.availableCopies++;
  loans = loans.filter(l => l.id !== loanId);
  saveData();
  renderLoans();
  renderBooks();
  updateLendingDropdowns();
}