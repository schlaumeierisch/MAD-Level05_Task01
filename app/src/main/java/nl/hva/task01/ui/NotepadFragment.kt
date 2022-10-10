package nl.hva.task01.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import nl.hva.task01.databinding.FragmentNotepadBinding
import nl.hva.task01.viewmodel.NoteViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotepadFragment : Fragment() {

    private var _binding: FragmentNotepadBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotepadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAddNoteResult()
    }

    private fun observeAddNoteResult() {
        viewModel.note.observe(viewLifecycleOwner) { note ->
            note?.let {
                binding.tvLastUpdated.text = it.date.toString()
                binding.tvNoteTitle.text = it.title
                binding.tvNoteText.text = it.text
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}