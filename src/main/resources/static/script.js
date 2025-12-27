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
      <td style="text-align:center;">
        <button class="btn-icon-edit" title="Edit" onclick="editStudent(${s.id})">✎</button>
        <button class="btn-icon-delete" title="Delete" onclick="deleteStudent(${s.id})">🗑</button>
      </td>
    </tr>
  `).join('');
  renderPagination('students', students.length, state, 'usersPagination');
}

let deleteStudentId = null;

function deleteStudent(id) {
  deleteStudentId = id;
  document.getElementById('deleteConfirmModal').style.display = 'block';
}

async function confirmDeleteStudent() {
  if (deleteStudentId) {
    await fetch(`${api.students}/${deleteStudentId}`, { method: 'DELETE' });
    await fetchStudents();
    cancelDeleteStudent();
  }
}

function cancelDeleteStudent() {
  deleteStudentId = null;
  document.getElementById('deleteConfirmModal').style.display = 'none';
}

function editStudent(id) {
  const student = students.find(s => s.id === id);
  if (!student) return;
  document.getElementById('editStudentId').value = student.id;
  document.getElementById('editStudentName').value = student.name;
  document.getElementById('editStudentStudentId').value = student.studentId;
  document.getElementById('editStudentClassName').value = student.className || '';
  document.getElementById('editStudentModal').style.display = 'block';
}

async function saveStudent(e) {
  e.preventDefault();
  const id = document.getElementById('editStudentId').value;
  const name = document.getElementById('editStudentName').value;
  const studentId = document.getElementById('editStudentStudentId').value;
  const className = document.getElementById('editStudentClassName').value;
  
  const payload = { name, studentId, className };
  await fetch(`${api.students}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });
  await fetchStudents();
  closeEditStudentModal();
}

function closeEditStudentModal() {
  document.getElementById('editStudentModal').style.display = 'none';
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
      <td style="text-align:center;">
        <button class="btn-icon-edit" title="Edit" onclick="editBook(${b.id})">✎</button>
        <button class="btn-icon-delete" title="Delete" onclick="deleteBook(${b.id})">🗑</button>
      </td>
    </tr>
  `).join('');
  renderPagination('books', books.length, state, 'booksPagination');
}

let deleteBookId = null;

function deleteBook(id) {
  deleteBookId = id;
  document.getElementById('deleteConfirmModal').style.display = 'block';
}

async function confirmDelete() {
  if (deleteBookId) {
    await fetch(`${api.books}/${deleteBookId}`, { method: 'DELETE' });
    await fetchBooks();
    cancelDelete();
  }
}

function cancelDelete() {
  deleteBookId = null;
  document.getElementById('deleteConfirmModal').style.display = 'none';
}

function editBook(id) {
  const book = books.find(b => b.id === id);
  if (!book) return;
  document.getElementById('editBookId').value = book.id;
  document.getElementById('editBookTitle').value = book.title;
  document.getElementById('editBookAuthor').value = book.author;
  document.getElementById('editBookISBN').value = book.isbn || '';
  document.getElementById('editBookCopies').value = book.copiesTotal || '';
  document.getElementById('editBookModal').style.display = 'block';
}

async function saveBook(e) {
  e.preventDefault();
  const id = document.getElementById('editBookId').value;
  const title = document.getElementById('editBookTitle').value;
  const author = document.getElementById('editBookAuthor').value;
  const isbn = document.getElementById('editBookISBN').value;
  const copies = parseInt(document.getElementById('editBookCopies').value);

  const payload = { title: title, author: author, isbn: isbn, copiesTotal: copies };
  await fetch(`${api.books}/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(payload) });
  document.getElementById('editBookModal').style.display = 'none';
  await fetchBooks();
}

function closeEditModal() {
  document.getElementById('editBookModal').style.display = 'none';
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
window.editBook = editBook;
window.closeEditModal = closeEditModal;
window.deleteBook = deleteBook;
window.confirmDelete = confirmDelete;
window.cancelDelete = cancelDelete;
window.deleteStudent = deleteStudent;
window.confirmDeleteStudent = confirmDeleteStudent;
window.cancelDeleteStudent = cancelDeleteStudent;
window.editStudent = editStudent;
window.saveStudent = saveStudent;
window.closeEditStudentModal = closeEditStudentModal;