package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Address;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.BillSummary;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.factory.BillFactory;
import com.airtribe.meditrack.observer.ConsoleNotificationObserver;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.ConsoleAppointmentReport;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.AIHelper;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.DateUtil;
import com.airtribe.meditrack.util.DoctorComparators;
import com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private final PatientService patientService = new PatientService();
    private final DoctorService doctorService = new DoctorService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final IdGenerator idGenerator = IdGenerator.getLazyInstance();

    public static void main(String[] args) {
        Main app = new Main();
        app.appointmentService.addObserver(new ConsoleNotificationObserver());
        app.seedData();
        if (containsArg(args, "--loadData")) {
            app.loadData();
        }
        app.menuLoop();
    }

    private static boolean containsArg(String[] args, String expected) {
        for (String arg : args) {
            if (expected.equalsIgnoreCase(arg)) {
                return true;
            }
        }
        return false;
    }

    private void seedData() {
        doctorService.addDoctor(new Doctor(idGenerator.nextId("DOC"), "Dr. Shah", "9876543210", Specialization.CARDIOLOGIST, 1000));
        doctorService.addDoctor(new Doctor(idGenerator.nextId("DOC"), "Dr. Roy", "9123456789", Specialization.DERMATOLOGIST, 800));
        patientService.addPatient(new Patient(idGenerator.nextId("PAT"), "Ankit", "9000011111", 32, new Address("Lake Road", "Kolkata")));
        patientService.addPatient(new Patient(idGenerator.nextId("PAT"), "Mira", "9000022222", 66, new Address("MG Road", "Bengaluru")));
    }

    private void loadData() {
        List<Patient> patients = CSVUtil.loadPatients(Constants.PATIENT_CSV_PATH);
        List<Doctor> doctors = CSVUtil.loadDoctors(Constants.DOCTOR_CSV_PATH);
        patients.forEach(patientService::addPatient);
        doctors.forEach(doctorService::addDoctor);

        Map<String, Patient> patientById = new HashMap<>();
        for (Patient p : patientService.getAllPatients()) {
            patientById.put(p.getId(), p);
        }
        Map<String, Doctor> doctorById = new HashMap<>();
        for (Doctor d : doctorService.getAllDoctors()) {
            doctorById.put(d.getId(), d);
        }
        List<Appointment> appointments = CSVUtil.loadAppointments(Constants.APPOINTMENT_CSV_PATH, patientById, doctorById);
        appointments.forEach(appointmentService::createAppointment);
        System.out.println("Data loaded from CSV.");
    }

    private void saveData() {
        CSVUtil.savePatients(Constants.PATIENT_CSV_PATH, patientService.getAllPatients());
        CSVUtil.saveDoctors(Constants.DOCTOR_CSV_PATH, doctorService.getAllDoctors());
        CSVUtil.saveAppointments(Constants.APPOINTMENT_CSV_PATH, appointmentService.getAllAppointments());
        System.out.println("Data saved to CSV.");
    }

    private void menuLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n==== MediTrack Menu ====");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Create Appointment");
            System.out.println("4. View Appointments");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Search Patient");
            System.out.println("7. Doctor Analytics (Streams)");
            System.out.println("8. Save Data");
            System.out.println("9. AI Doctor Recommendation");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addPatient(scanner);
                case 2 -> addDoctor(scanner);
                case 3 -> createAppointment(scanner);
                case 4 -> appointmentService.getAllAppointments().forEach(System.out::println);
                case 5 -> cancelAppointment(scanner);
                case 6 -> searchPatient(scanner);
                case 7 -> doctorAnalytics();
                case 8 -> saveData();
                case 9 -> suggestDoctor(scanner);
                case 0 -> {
                    System.out.println("Exiting MediTrack.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addPatient(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone (10 digits): ");
        String phone = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Address line: ");
        String line = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();

        Patient patient = new Patient(idGenerator.nextId("PAT"), name, phone, age, new Address(line, city));
        patientService.addPatient(patient);
        System.out.println("Patient added: " + patient);
    }

    private void addDoctor(Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone (10 digits): ");
        String phone = scanner.nextLine();
        System.out.print("Specialization " + List.of(Specialization.values()) + ": ");
        Specialization specialization = Specialization.valueOf(scanner.nextLine().trim().toUpperCase());
        System.out.print("Consultation fee: ");
        double fee = Double.parseDouble(scanner.nextLine());

        Doctor doctor = new Doctor(idGenerator.nextId("DOC"), name, phone, specialization, fee);
        doctorService.addDoctor(doctor);
        System.out.println("Doctor added: " + doctor);
    }

    private void createAppointment(Scanner scanner) {
        System.out.print("Patient ID: ");
        String patientId = scanner.nextLine();
        System.out.print("Doctor ID: ");
        String doctorId = scanner.nextLine();
        System.out.print("Date/Time (yyyy-MM-dd HH:mm): ");
        LocalDateTime dateTime = DateUtil.parse(scanner.nextLine());

        Patient patient = patientService.searchPatient(patientId);
        Doctor doctor = doctorService.getDoctorById(doctorId);
        if (patient == null || doctor == null) {
            System.out.println("Invalid patient/doctor ID.");
            return;
        }

        Appointment appointment = appointmentService.createAppointment(idGenerator.nextId("APT"), patient, doctor, dateTime);
        Bill bill = BillFactory.createConsultationBill(patient, doctor.getConsultationFee());
        BillSummary billSummary = new BillSummary(patient.getId(), bill.getTotal(), LocalDateTime.now());
        System.out.println("Appointment created: " + appointment);
        System.out.println("Bill generated: " + bill);
        System.out.println("Bill summary immutable object: patient=" + billSummary.getPatientId()
                + ", total=" + billSummary.getTotalAmount() + ", at=" + billSummary.getGeneratedAt());

        Appointment clone = appointment.clone();
        clone.getPatient().getAddress().setCity("ChangedCity");
        System.out.println("Clone deep-copy check: originalCity=" + appointment.getPatient().getAddress().getCity()
                + ", cloneCity=" + clone.getPatient().getAddress().getCity());
    }

    private void cancelAppointment(Scanner scanner) {
        System.out.print("Appointment ID: ");
        String appointmentId = scanner.nextLine();
        System.out.println("Cancelled: " + appointmentService.cancelAppointment(appointmentId));
    }

    private void searchPatient(Scanner scanner) {
        System.out.println("Search by: 1) ID 2) Name 3) Age");
        int mode = Integer.parseInt(scanner.nextLine());
        if (mode == 1) {
            System.out.print("ID: ");
            System.out.println(patientService.searchPatient(scanner.nextLine()));
        } else if (mode == 2) {
            System.out.print("Name: ");
            patientService.searchPatient(scanner.nextLine(), true).forEach(System.out::println);
        } else if (mode == 3) {
            System.out.print("Age: ");
            patientService.searchPatient(Integer.parseInt(scanner.nextLine())).forEach(System.out::println);
        } else {
            System.out.println("Invalid mode.");
        }
    }

    private void doctorAnalytics() {
        System.out.println("Average consultation fee: " + doctorService.averageConsultationFee());
        Map<String, Long> counts = doctorService.appointmentsPerDoctor(appointmentService.getAllAppointments());
        System.out.println("Appointments per doctor: " + counts);
        System.out.println("Doctors sorted by fee:");
        doctorService.getAllDoctors().stream().sorted(DoctorComparators.BY_FEE).forEach(System.out::println);
        new ConsoleAppointmentReport().generate(appointmentService.getAllAppointments());
    }

    private void suggestDoctor(Scanner scanner) {
        System.out.print("Enter symptoms: ");
        String symptoms = scanner.nextLine();
        Specialization specialization = AIHelper.recommendSpecialization(symptoms);
        System.out.println("Recommended specialization: " + specialization);
        List<Doctor> doctors = doctorService.filterBySpecialization(specialization);
        if (doctors.isEmpty()) {
            System.out.println("No exact match. Try General Physician.");
            doctors = doctorService.filterBySpecialization(Specialization.GENERAL_PHYSICIAN);
        }
        doctors.forEach(System.out::println);
    }
}
