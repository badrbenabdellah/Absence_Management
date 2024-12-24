import React, { useState, useEffect } from 'react';
import { Search, Plus, ChevronDown, Edit2, Trash2 } from 'lucide-react';
import {
    getRecentAbsences,
    addAbsence,
    deleteAbsence,
    updateAbsence,
} from './api/absence'; // Modifier avec le chemin correct pour vos fonctions API
import './AbsencesTable.css'; // Si des styles spécifiques sont nécessaires

function AbsencesTable() {
    const [absences, setAbsences] = useState([]);
    const [filteredAbsences, setFilteredAbsences] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [editingAbsence, setEditingAbsence] = useState(null);
    const absencesPerPage = 10;

    useEffect(() => {
        async function fetchAbsences() {
            try {
                const data = await getRecentAbsences(90); // Remplacez avec votre logique d'API
                setAbsences(data);
                setFilteredAbsences(data);
            } catch (error) {
                console.error('Erreur lors de la récupération des absences :', error);
            }
        }

        fetchAbsences();
    }, []);

    useEffect(() => {
        const results = absences.filter((absence) =>
            absence.student.firstName.toLowerCase().includes(searchTerm.toLowerCase()) ||
            absence.student.lastName.toLowerCase().includes(searchTerm.toLowerCase()) ||
            absence.justification?.toLowerCase().includes(searchTerm.toLowerCase())
        );
        setFilteredAbsences(results);
    }, [searchTerm, absences]);

    const handleAddAbsence = async (newAbsence) => {
        try {
            const addedAbsence = await addAbsence(newAbsence);
            setAbsences((prev) => [...prev, addedAbsence]);
            setIsAddModalOpen(false);
        } catch (error) {
            console.error('Erreur lors de l’ajout de l’absence :', error);
        }
    };

    const handleEditAbsence = async (updatedAbsence) => {
        try {
            const editedAbsence = await updateAbsence(updatedAbsence.id, {
                date: updatedAbsence.date,
                justification: updatedAbsence.justification,
                student: updatedAbsence.student,
            });
            setAbsences((prev) =>
                prev.map((absence) => (absence.id === editedAbsence.id ? editedAbsence : absence))
            );
            setIsEditModalOpen(false);
            setEditingAbsence(null);
        } catch (error) {
            console.error('Erreur lors de la mise à jour de l’absence :', error);
        }
    };

    const handleDeleteAbsence = async (id) => {
        try {
            await deleteAbsence(id);
            setAbsences((prev) => prev.filter((absence) => absence.id !== id));
        } catch (error) {
            console.error('Erreur lors de la suppression de l’absence :', error);
        }
    };

    const indexOfLastAbsence = currentPage * absencesPerPage;
    const indexOfFirstAbsence = indexOfLastAbsence - absencesPerPage;
    const currentAbsences = filteredAbsences.slice(indexOfFirstAbsence, indexOfLastAbsence);

    return (
        <div className="space-y-4">
            {/* Barre de recherche et bouton d'ajout */}
            <div className="flex justify-between items-center">
                <div className="flex items-center space-x-4">
                    <div className="relative">
                        <input
                            type="text"
                            placeholder="Rechercher une absence..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="w-full sm:w-64 pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
                        />
                        <Search className="absolute left-3 top-2.5 h-5 w-5 text-gray-400" />
                    </div>
                </div>
                <button
                    onClick={() => setIsAddModalOpen(true)}
                    className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                    <Plus className="h-5 w-5 mr-2" />
                    Ajouter une absence
                </button>
            </div>

            {/* Tableau des absences */}
            <div className="bg-white shadow overflow-hidden sm:rounded-lg">
                <table className="min-w-full divide-y divide-gray-200">
                    <thead className="bg-gray-50">
                    <tr>
                        {/* Vos colonnes ici */}
                    </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                    {currentAbsences.map((absence) => (
                        <tr key={absence.id}>
                            {/* Vos données ici */}
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            {/* Pagination */}
            {/* Ajoutez le code pour la pagination */}

            {/* Modaux */}
            {isAddModalOpen && (
                <AddAbsenceModal
                    onClose={() => setIsAddModalOpen(false)}
                    onSubmit={handleAddAbsence}
                />
            )}
            {isEditModalOpen && editingAbsence && (
                <EditAbsenceModal
                    onClose={() => setIsEditModalOpen(false)}
                    absence={editingAbsence}
                    onSubmit={handleEditAbsence}
                />
            )}
        </div>
    );
}

export default AbsencesTable;
