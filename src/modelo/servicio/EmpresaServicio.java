package modelo.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import exceptions.InstanceNotFoundException;
import modelo.Empleados;
import modelo.Empresas;
import util.SessionFactoryUtil;

public class EmpresaServicio implements IEmpresaServicio {

	@Override
	public Empresas create(Empresas empresa) {
		SessionFactory sf = SessionFactoryUtil.getSessionFactory();

		Transaction tx = null;
		try (Session session = sf.openSession()) {
			tx = session.beginTransaction();
			session.save(empresa);
			tx.commit();

		} catch (Exception ex) {
			System.out.println("Ha ocurrido una exception: " + ex.getMessage());

			if (tx != null) {
				tx.rollback();
			}
			empresa = null;
		}
		return empresa;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empresas> getAllEmpresasYEmpleados() {
		SessionFactory sf = SessionFactoryUtil.getSessionFactory();
		List<Empresas> empresas = new ArrayList<>();
		Set<Empleados> empleados;

		Transaction tx = null;
		try (Session session = sf.openSession()) {
			tx = session.beginTransaction();

			empresas = session.createQuery("select e from Empresas e order by e.nombre").list();
			// Si no se fuerza la carga de los empleados, se obtendrá una
			// LazyInitializationException
			for (Empresas cia : empresas) {
				empleados = cia.getEmpleados();
				for (Empleados employee : empleados) {
					System.out.println("Forzando la carga de los empleados");
					employee.getNombre();
				}
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una exception: " + ex.getMessage());
			if (tx != null) {
				tx.rollback();
			}

		}
		return empresas;
	}

	@Override
	public boolean delete(String cif) throws Exception {
		boolean exito = false;

		SessionFactory sf = SessionFactoryUtil.getSessionFactory();
		Transaction tx = null;
		try (Session session = sf.openSession()) {
			tx = session.beginTransaction();
			Empresas cia = session.get(Empresas.class, cif);
			if (cia != null) {
				session.remove(cia);
			} else {
				throw new InstanceNotFoundException(Empresas.class.getName());
			}
			tx.commit();
			exito = true;
		} catch (Exception ex) {
			System.out.println("Ha ocurrido una exception: " + ex.getMessage());

			if (tx != null) {
				tx.rollback();
			}

			throw ex;
		}
		return exito;
	}

}
