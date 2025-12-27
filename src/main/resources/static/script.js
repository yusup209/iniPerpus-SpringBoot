// Get data from localStorage or initialize empty arrays
let students = [];
let books = [];
let loans = [];

const paging = {
  students: { page: 1, size: 5 },
  books: { page: 1, size: 5 },
  loans: { page: 1, size: 5 }
};

const api = {
  students: '/api/students',
  books: '/api/books',
  lendings: '/api/lendings'
};

function setLendingStatus(message, isError = false) {
  const el = document.getElementById('lendingStatus');
  if (!el) return;
  el.textContent = message || '';
  el.style.color = isError ? '#b00020' : '#0a6d0a';
}

// Student Management
async function addStudent(e) {
  e.preventDefault();
  const name = document.getElementById('userName').value;
  const studentId = document.getElementById('userEmail').value; // reuse email field as studentId input field id
  const className = document.getElementById('userPhone').value;

  const payload = { name: name, studentId: studentId, className: className };
  await fetch(api.students, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
  await fetchStudents();
  e.target.reset();
}

function ensureValidPage(state, total) {
  const maxPages = Math.max(1, Math.ceil(total / state.size));
  if (state.page > maxPages) state.page = maxPages;
  if (state.page < 1) state.page = 1;
  return maxPages;
}

function renderPagination(kind, total, state, elId) {
  const el = document.getElementById(elId);
  if (!el) return;
  const maxPages = Math.max(1, Math.ceil(total / state.size));
  el.innerHTML = `
    <div style="display:flex;gap:12px;align-items:center;">
      <span>Rows per page</span>
      <select onchange="setPageSize('${kind}', this.value)">
        <option value="5" ${state.size==5?'selected':''}>5</option>
        <option value="10" ${state.size==10?'selected':''}>10</option>
        <option value="25" ${state.size==25?'selected':''}>25</option>
      </select>
      <span>Page ${state.page} / ${maxPages}</span>
      <button onclick="changePage('${kind}', -1)">Prev</button>
      <button onclick="changePage('${kind}', 1)">Next</button>
    </div>`;
}

function renderStudents() {
  const tbody = document.getElementById('usersBody');
  if (!tbody) return;
  const state = paging.students;
  const maxPages = ensureValidPage(state, students.length);
  const start = (state.page - 1) * state.size;
  const pageRows = students.slice(start, start + state.size);

  tbody.innerHTML = pageRows.map(s => `
    <tr>
      <td>${s.name}</td>
      <td>${s.studentId}</td>
      <td>${s.className || '—'}</td>
      <td><button class="btn-delete" onclick="deleteStudent(${s.id})">Delete</button></td>
    </tr>
  `).join('');
  renderPagination('students', students.length, state, 'usersPagination');
}

async function deleteStudent(id) {
  await fetch(`${api.students}/${id}`, { method: 'DELETE' });
  await fetchStudents();
}

async function fetchStudents() {
  const resp = await fetch(api.students, { credentials: 'same-origin', headers: { 'Accept': 'application/json' } });
  students = await resp.json();
  renderStudents();
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
  const state = paging.books;
  const maxPages = ensureValidPage(state, books.length);
  const start = (state.page - 1) * state.size;
  const pageRows = books.slice(start, start + state.size);

  tbody.innerHTML = pageRows.map(b => `
    <tr>
      <td>${b.title}</td>
      <td>${b.author}</td>
      <td>${b.isbn}</td>
      <td>${b.copiesAvailable || b.copiesAvailable === 0 ? b.copiesAvailable : b.availableCopies}/${b.copiesTotal || b.totalCopies}</td>
      <td><button class="btn-delete" onclick="deleteBook(${b.id})">Delete</button></td>
    </tr>
  `).join('');
  renderPagination('books', books.length, state, 'booksPagination');
}

async function deleteBook(id) {
  await fetch(`${api.books}/${id}`, { method: 'DELETE' });
  await fetchBooks();
}

async function fetchBooks() {
  const resp = await fetch(api.books, { credentials: 'same-origin', headers: { 'Accept': 'application/json' } });
  books = await resp.json();
  renderBooks();
  updateLendingDropdowns();
}

// Lending Management
function updateLendingDropdowns() {
  const userSelect = document.getElementById('lendUser');
  const bookSelect = document.getElementById('lendBook');

  if (!userSelect || !bookSelect) return;

  userSelect.innerHTML = '<option value="">Select a student</option>' +
    students.map(s => `<option value="${s.id}">${s.name || s.studentId}</option>`).join('');

  bookSelect.innerHTML = '<option value="">Select a book</option>' +
    books.filter(b => (b.copiesAvailable || b.copiesAvailable === 0 ? b.copiesAvailable : b.availableCopies) > 0)
      .map(b => `<option value="${b.id}">${b.title} (${b.copiesAvailable || b.availableCopies} available)</option>`).join('');
}

async function lendBook(e) {
  console.log('lendBook called with event:', e);
  e.preventDefault();
  console.log('preventDefault executed');
  const studentId = parseInt(document.getElementById('lendUser').value);
  const bookId = parseInt(document.getElementById('lendBook').value);
  const dueDate = document.getElementById('lendDueDate').value || null;

  if (!studentId || !bookId) {
    alert('Please select both student and book.');
    return;
  }

  const payload = { studentId: studentId, bookId: bookId, dueDate: dueDate };
  console.log('Lending payload:', payload);
  try {
    const resp = await fetch(api.lendings, {
      method: 'POST',
      credentials: 'same-origin',
      headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
      body: JSON.stringify(payload)
    });
    console.log('Lend response status:', resp.status);
    const respText = await resp.text();
    console.log('Lend response body:', respText);
    
    if (!resp.ok) {
      alert('Failed to lend: ' + (respText || resp.status));
      setLendingStatus('Failed to lend: ' + (respText || resp.status), true);
      return;
    }
    setLendingStatus('Lending saved.', false);
    await fetchLoans();
    await fetchBooks();
    if (e && e.target && e.target.form) {
      e.target.form.reset();
    } else {
      const btn = document.getElementById('lendBookBtn');
      const form = (btn && btn.closest('form')) || document.querySelector('form');
      if (form) form.reset();
    }
  } catch (err) {
    console.error('Lend error:', err);
    alert('Error lending book: ' + err);
    setLendingStatus('Error lending book: ' + err, true);
  }
}

function renderLoans() {
  const tbody = document.getElementById('lendingBody');
  if (!tbody) return;
  const state = paging.loans;
  const maxPages = ensureValidPage(state, loans.length);
  const start = (state.page - 1) * state.size;
  const pageRows = loans.slice(start, start + state.size);

  tbody.innerHTML = pageRows.map(l => `
    <tr>
      <td>${l.borrower ? (l.borrower.name || l.borrower.studentId) : '—'}</td>
      <td>${l.book ? l.book.title : '—'}</td>
      <td>${l.dueDate ? l.dueDate : ''}</td>
      <td><span class="status-badge">${l.returnDate ? 'returned' : 'borrowed'}</span></td>
      <td>${l.returnDate ? '' : `<button class="btn-delete" onclick="returnBook(${l.id})">Return</button>`}</td>
    </tr>
  `).join('');
  renderPagination('loans', loans.length, state, 'lendingPagination');
}

async function returnBook(loanId) {
  await fetch(`${api.lendings}/${loanId}/return`, { method: 'POST' });
  await fetchLoans();
  await fetchBooks();
}

async function fetchLoans() {
  const resp = await fetch(api.lendings, { credentials: 'same-origin', headers: { 'Accept': 'application/json' } });
  loans = await resp.json();
  renderLoans();
}

// Initial fetch
document.addEventListener('DOMContentLoaded', async () => {
  await fetchStudents();
  await fetchBooks();
  await fetchLoans();
  const btn = document.getElementById('lendBookBtn');
  if (btn) {
    console.log('Attaching click handler to lendBookBtn');
    btn.addEventListener('click', lendBook);
  } else {
    console.warn('lendBookBtn not found');
  }
});

function changePage(kind, delta) {
  const state = paging[kind];
  const total = kind === 'students' ? students.length : kind === 'books' ? books.length : loans.length;
  const max = ensureValidPage(state, total);
  state.page = Math.min(max, Math.max(1, state.page + delta));
  if (kind === 'students') renderStudents();
  else if (kind === 'books') renderBooks();
  else renderLoans();
}

function setPageSize(kind, size) {
  const state = paging[kind];
  state.size = parseInt(size);
  state.page = 1;
  if (kind === 'students') renderStudents();
  else if (kind === 'books') renderBooks();
  else renderLoans();
}

// Ensure functions used by inline handlers are globally accessible
window.lendBook = lendBook;
window.changePage = changePage;
window.setPageSize = setPageSize;