<!DOCTYPE html >
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="sat, 01 Dec 2001 00:00:00 GMT">
<title>Project management | home</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
	<script type="text/javascript">
		function checkForm(form) {
			if (form.password.value != ""
					&& form.password.value == form.verifyPassword.value) {
				return true;
			} else {
				alert("Error: password mismatch!");
				form.verifyPassword.focus();
				return false;
			}

			re = /^\d{4}-\d{2}-\d{2}$/;

			if (form.dob.value != '' && !form.dob.value.match(re)) {
				alert("Invalid date format: " + form.dob.value);
				form.dob.focus();
				return false;
			}
		}
	</script>
	<div role="navigation">
		<div class="navbar navbar-inverse">
			<a href="/" class="navbar-brand">Project management</a>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<c:if test="${userset!='YES'}">
						<li><a href="/login">Login</a></li>
						<li><a href="/register">New Registration</a></li>
					</c:if>

					<c:if test="${userset=='YES'}">
						<li><a href="/getAllEmployees">All Employees</a></li>
						<li><a href="/showDepartments">All Departments</a></li>
						<li><a href="/showAvailableProjects">All Projects</a></li>
						<li><a href="/showAllocations">Allocations</a></li>
						<li><a href="/details">My Details</a></li>
						<li><a style="margin-left: 800%;" href="/logout">logout</a>
					</c:if>
				</ul>
			</div>
		</div>
	</div>

	<c:choose>
		<c:when test="${mode=='MODE_HOME'}">
			<div class="container" id="homediv">
				<div class="jumbotron text-center">
					<h1>Welcome to TEKsystems project management</h1>

				</div>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_EMAIL'}">
			<div var="employee" var="departmentname" class="container"
				id="homediv">
				<c:if test="${not empty employee}">
					<h4>Resource is allocated in project</h4>
				</c:if>
				<c:if test="${not empty departmentname}">
					<h4>Projects assigned to department ${departmentname}</h4>
				</c:if>
				<c:if test="${not empty employee}">
					<button type="button" class="btn btn-info btn-lg"
						data-toggle="modal" data-target="#myModal">Send email to
						de-allocate ${employee.fname}</button>
				</c:if>
				<c:if test="${not empty departmentname}">
					<button type="button" class="btn btn-info btn-lg"
						data-toggle="modal" data-target="#myModal">Send email to
						remove projects</button>
				</c:if>
				<!-- Modal -->
				<div id="myModal" class="modal fade" role="dialog">
					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Email</h4>
							</div>
							<c:if test="${not empty employee}">
								<div class="modal-body">
									<p>Please de-allocate the employee ${employee.fname}</p>
								</div>
							</c:if>
							<c:if test="${not empty departmentname}">
								<div class="modal-body">
									<p>Please delete the projects in ${departmentname}</p>
								</div>
							</c:if>
							<div class="modal-footer">
								<c:if test="${not empty employee}">
									<button type="button"
										onclick="location.href='/sendEmail?user_id=${employee.user_id}'"
										class="btn btn-dark">Send</button>
								</c:if>
								<c:if test="${not empty departmentname}">
									<button type="button"
										onclick="location.href='/sendDepartmentEmail?departmentname=${departmentname}'"
										class="btn btn-dark">Send</button>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_ACCESSDENIED'}">
			<body>
				<div class="container" id="homediv">

					<h2>Sorry, you do not have permission to view this page.</h2>
				</div>
			</body>

		</c:when>

		<c:when test="${mode=='MODE_ERROR'}">
			<body var="errorName">
				<div class="container" id="homediv">
					<h2>Cannot execute operation</h2>
					<h4>${errorName}</h4>
				</div>
			</body>

		</c:when>

		<c:when test="${mode=='MODE_REGISTER'}">
			<div class="container text-center">
				<h3>New Registration</h3>
				<hr>
				<form class="form-horizontal" method="POST" action="saveEmployee"
					onsubmit="return checkForm(this)" enctype="multipart/form-data"
					id="fileUploadForm">
					<c:if test="${not empty beanError}">
						<div class="well well-sm">
							<h5>${beanError}</h5>
						</div>
					</c:if>
					<input type="hidden" name="id" value="${employee.user_id}" />
					<div class="form-group">
						<label class="control-label col-md-3">First Name</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="fname"
								value="${employee.fname}" required />

						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Last Name</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="lname"
								value="${employee.lname}" required />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Email </label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="email"
								value="${employee.email}" required />
							<form:errors path="email" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">DOB(yyyy-mm-dd)</label>
						<div class="col-md-7">
							<input type="date" class="form-control" name="dob"
								value="${employee.dob}" required />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Password</label>
						<div class="col-md-7">
							<input type="password" class="form-control" name="password"
								value="${password}" required />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Confirm Password</label>
						<div class="col-md-7">
							<input type="password" class="form-control" name="verifyPassword"
								value="${verifyPassword}" required />
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-md-3" for="uploadfile">Upload
							File</label>
						<div class="col-md-7">

							<input type="file" class="form-control" id="uploadfile"
								placeholder="Upload File" name="uploadfile"></input>
							<button type="button" class="btn btn-default" id="btnSubmit">Upload</button>
						</div>
					</div>

					<div class="form-group ">
						<input type="submit" class="btn btn-primary" value="Register" />
					</div>
				</form>
				<c:if test="${userexists=='YES'}">
					<div class="well well-sm">
						<h5>User already exists with given email, enter different
							email.</h5>
					</div>
				</c:if>
			</div>
		</c:when>

		<c:when test="${mode=='ALL_ALLOCATIONS'}">
			<div class="container text-center" id="tasksDiv" var="allocations">
				<h3>Allocations</h3>
				<hr>
				<c:if test="${fn:length(allocations) gt 0}">
					<div class="table-responsive">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>project name</th>
									<th>department</th>
									<th>employees</th>
									<th>de-allocate</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="allocation" items="${allocations}">
									<tr>
										<td>${allocation.allocatedProject.name}</td>
										<td>${allocation.allocatedProject.department.departmentname}</td>
										<td>${allocation.employee.fname}</td>
										<td><a href="/deleteAllocation?p_id=${allocation.p_id}"><span
												class="glyphicon glyphicon-trash"></span></a></td>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<c:if test="${role=='manager'}">
					<div class="form-group ">
						<button type="button" onclick="location.href='/allocateToProject'"
							class="btn btn-primary">Allocate</button>
					</div>
				</c:if>
			</div>
		</c:when>


		<c:when test="${mode=='ALL_EMPLOYEES'}">
			<div class="container text-center" id="tasksDiv">
				<h3>All Employees</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Id</th>
								<th>FirstName</th>
								<th>LastName</th>
								<th>Email</th>
								<th>DOB</th>
								<th>Role</th>
								<c:if test="${role=='admin' or role=='manager'}">
									<th>Resume</th>
								</c:if>
								<c:if test="${role=='admin'}">
									<th>Delete</th>
									<th>Edit</th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="employee" items="${employees}">
								<c:if test="${role=='admin'}">
									<tr>
										<td>${employee.user_id}</td>
										<td>${employee.fname}</td>
										<td>${employee.lname}</td>
										<td>${employee.email}</td>
										<td>${employee.dob}</td>
										<td>${employee.role}</td>
										<td><a href="/downloadfile?id=${employee.file.id}"><span
												class="glyphicon glyphicon-download-alt"></span></a></td>
										<c:if test="${role=='admin'}">
											<td><a
												href="/deleteEmployee?user_id=${employee.user_id}"
												data-toggle="confirmation"
												data-title="Employee allocated, send email to manager?"><span
													class="glyphicon glyphicon-trash"></span></a></td>
											<td><a data-toggle="confirmation"
												data-title="Delete employee?"
												href="/updateEmployee?user_id=${employee.user_id}"><span
													class="glyphicon glyphicon-pencil"></span></a></td>
										</c:if>
									</tr>
								</c:if>

								<c:if test="${role=='manager'}">
									<c:if
										test="${employee.role!='manager' and employee.role!='admin'}">
										<tr>
											<td>${employee.user_id}</td>
											<td>${employee.fname}</td>
											<td>${employee.lname}</td>
											<td>${employee.email}</td>
											<td>${employee.dob}</td>
											<td>${employee.role}</td>
											<td><a href="/downloadfile?id=${employee.file.id}"><span
													class="glyphicon glyphicon-download-alt"></span></a></td>
										</tr>
									</c:if>
									
								</c:if>

							</c:forEach>
						</tbody>
					</table>
					<c:if test="${a==0}">
						<div class="well well-sm">
							<h5>no employees</h5>
						</div>
					</c:if>
				</div>
				<c:if test="${emailSent=='yes'}">
					<div class="well well-sm">
						<h5>Email has been sent, employee will be de-allocated
							shortly</h5>
					</div>
				</c:if>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_EMPLOYEEDETAILS'}">
			<div class="container text-center" id="tasksDiv">
				<h3>Welcome
					${allocations.employee.fname}(${allocations.employee.role})</h3>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>FirstName</th>
								<th>LastName</th>
								<th>Email</th>
								<th>DOB</th>
								<th>Role</th>
								<th>project name</th>
								<th>department</th>
							</tr>
						</thead>
						<tbody var="allocations">
							<tr>
								<td>${allocations.employee.fname}</td>
								<td>${allocations.employee.lname}</td>
								<td>${allocations.employee.email}</td>
								<td>${allocations.employee.dob}</td>
								<td>${allocations.employee.role}</td>
								<td>${allocations.allocatedProject.name}</td>
								<td>${allocations.department.departmentname}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_EMPLOYEEDETAILSNULL'}">
			<div class="container text-center" id="tasksDiv" var="allocations">
				<c:if test="${allocations.role=='employee'}">
				<h3>Welcome ${allocations.fname}(not allocated to any project)</h3>
				</c:if>
				<c:if test="${allocations.role=='admin' or allocations.role=='manager'}">
				<h3>Welcome ${allocations.fname}(${allocations.role})</h3>
				</c:if>
				<hr>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>FirstName</th>
								<th>LastName</th>
								<th>Email</th>
								<th>DOB</th>
								<th>Role</th>
							</tr>
						</thead>
						<tbody var="allocations">
							<tr>
								<td>${allocations.fname}</td>
								<td>${allocations.lname}</td>
								<td>${allocations.email}</td>
								<td>${allocations.dob}</td>
								<td>${allocations.role}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_UPDATE'}">
			<div class="container text-center">
				<h3>Update User</h3>
				<hr>
				<form class="form-horizontal" method="PUT"
					action="saveUpdatedEmployee">
					<input type="hidden" name="id" value="${employee.user_id}" />
					<div class="form-group">
						<label class="control-label col-md-3">ID</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="user_id"
								value="${employee.user_id}" readonly />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">First name</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="fname"
								value="${employee.fname}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Last Name</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="lname"
								value="${employee.lname}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Email </label>
						<div class="col-md-3">
							<input type="text" class="form-control" name="email"
								value="${employee.email}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">dob</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="dob"
								value="${employee.dob}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Role</label>
						<div class="col-md-7">

							<input type="text" class="form-control" name="role"
								value="${employee.role}" readonly />
						</div>
					</div>
					<div class="form-group ">
						<input type="submit" class="btn btn-primary" value="Update" />
					</div>
				</form>
			</div>
		</c:when>

		<c:when test="${mode=='ALL_DEPARTMENTS'}">
			<div class="container text-center" id="tasksDiv" var="departments">
				<h3>All Departments</h3>
				<hr>
				<c:if test="${fn:length(departments) gt 0}">
					<div class="table-responsive">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Department Id</th>
									<th>Department Name</th>
									<th>Department manager</th>
									<c:if test="${role=='admin'}">
										<th>Delete</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="department" items="${departments}">
									<tr>
										<td>${department.department_id}</td>
										<td>${department.departmentname}</td>
										<td>${department.employee.fname}</td>
										<c:if test="${role=='admin'}">
											<td><a
												href="/deleteDepartment?department_id=${department.department_id}"
												onclick="return confirm('Are you sure you want to delete?')"><span
													class="glyphicon glyphicon-trash"></span></a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<c:if test="${role=='admin'}">
					<div>
						<button type="button"
							onclick="location.href='/registerDepartment'"
							class="btn btn-primary">Add department</button>
						<!--  <a href="/registerDepartment">Add new department</a> -->
					</div>
				</c:if>
				<c:if test="${emailSent=='yes'}">
					<div class="well well-sm">
						<h5>Email has been sent, projects will be removed shortly</h5>
					</div>
				</c:if>
			</div>

		</c:when>

		<c:when test="${mode=='MODE_DREGISTER'}">
			<div class="container text-center">
				<h3>New Department Registration</h3>
				<hr>
				<form class="form-horizontal" method="POST"
					action="addNewDepartment">
					<div class="form-group">
						<label class="control-label col-md-3">Department Name</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="departmentname"
								value="${departmentname}" required />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Department Manager
							id</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="user_id"
								value="${user_id}" required />
						</div>
					</div>
					<div class="form-group ">
						<input type="submit" class="btn btn-primary" value="Register" />
					</div>
				</form>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_ALLOCATE'}">
			<div class="container text-center">
				<h3>New allocation</h3>
				<hr>
				<form class="form-horizontal" method="POST" action="addAllocation">
					<div class="form-group">
						<label class="control-label col-md-3">Enter project id</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="a_id"
								value="${a_id}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Enter user id id</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="user_id"
								value="${user_id}" />
						</div>
					</div>
					<div class="form-group ">
						<input type="submit" class="btn btn-primary" value="Allocate" />
					</div>
				</form>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_PREGISTER'}">
			<div class="container text-center">
				<h3>New Registration</h3>
				<hr>
				<form class="form-horizontal" method="POST"
					action="registerNewAvailableProject">
					<input type="hidden" name="id" value="${availableproject.p_id}" />
					<div class="form-group">
						<label class="control-label col-md-3">Project Name</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="name"
								value="${availableproject.name}" required />
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-md-3">Start date</label>
						<div class="col-md-7">
							<input type="date" class="form-control" name="start_date"
								value="${availableproject.start_date}" required />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">End date</label>
						<div class="col-md-7">
							<input type="date" class="form-control" name="end_date"
								value="${availableproject.end_date}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Project description</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="description"
								value="${availableproject.description}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">project department</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="department_id"
								value="${department_id}" />
						</div>
					</div>
					<div class="form-group ">
						<input type="submit" class="btn btn-primary" value="Register" />
					</div>
				</form>
			</div>
		</c:when>

		<c:when test="${mode=='ALL_PROJECTS'}">
			<div class="container text-center" id="tasksDiv"
				var="availibleprojects">
				<h3>All Projects</h3>
				<hr>
				<c:if test="${fn:length(availibleprojects) gt 0}">
					<div class="table-responsive">
						<table class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Project Id</th>
									<th>Project Name</th>
									<th>Project description</th>
									<th>Project startDate</th>
									<th>Project endDate</th>
									<th>Department</th>
									<c:if test="${role=='manager'}">
										<th>Delete</th>
									</c:if>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="availibleproject" items="${availibleprojects}">
									<tr>
										<td>${availibleproject.a_id}</td>
										<td>${availibleproject.name}</td>
										<td>${availibleproject.description}</td>
										<td>${availibleproject.start_date}</td>
										<td>${availibleproject.end_date}</td>
										<td>${availibleproject.department.departmentname}</td>
										<c:if test="${role=='manager'}">
											<td><a
												href="/deleteAvailableProject?a_id=${availibleproject.a_id}"><span
													class="glyphicon glyphicon-trash"></span></a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<c:if test="${role=='manager'}">
					<div class="form-group ">
						<button type="button"
							onclick="location.href='/registerAvailableProject'"
							class="btn btn-primary">Add project</button>
					</div>
				</c:if>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_LOGIN' }">
			<div class="container text-center">
				<h3>Login</h3>
				<hr>
				<form class="form-horizontal	" method="POST" action="/login">

					<div class="form-group">
						<label class="control-label col-md-3">Username(email)</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="username"
								value="${username}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Password</label>
						<div class="col-md-7">
							<input type="password" class="form-control" name="password"
								value="${password}" />
						</div>
					</div>
					<div class="form-group ">
						<input type="submit" class="btn btn-primary" value="Login" />
					</div>
				</form>
			</div>
		</c:when>

		<c:when test="${mode=='MODE_LOGINERROR' }">
			<div class="container text-center">
				<h3>Login</h3>
				<hr>
				<form class="form-horizontal	" method="POST" action="/login">

					<div class="form-group">
						<label class="control-label col-md-3">Username(email)</label>
						<div class="col-md-7">
							<input type="text" class="form-control" name="username"
								value="${username}" />
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Password</label>
						<div class="col-md-7">
							<input type="password" class="form-control" name="password"
								value="${password}" />
						</div>
					</div>
					<div class="form-group ">
						<input type="submit" class="btn btn-primary" value="Login" />
					</div>
				</form>
				<div class="well well-sm">
					<h5>Invalid username or password</h5>
				</div>
			</div>
		</c:when>
	</c:choose>
	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>